package com.konami.bookSocial.auth;


import com.konami.bookSocial.email.EmailService;
import com.konami.bookSocial.email.EmailTemplateName;
import com.konami.bookSocial.role.RoleRepository;
import com.konami.bookSocial.security.JwtService;
import com.konami.bookSocial.user.Token;
import com.konami.bookSocial.user.TokenRepository;
import com.konami.bookSocial.user.User;
import com.konami.bookSocial.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    String activationUrl = "http://localhost:4200/activate-account";

    public void register(RegisterRequest registerRequest) throws MessagingException {
        var userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        var user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFirstName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate your account"
        );

    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String chars = "0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFirstName() + " " + user.getLastName());
        var jwt = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

//    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Token expired");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    public void logout(String token) {
        tokenBlacklistService.blacklistToken(token);
    }
}
