package com.konami.bookSocial.security;


import com.konami.bookSocial.auth.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final TokenBlacklistService tokenBlacklistService;
        private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String email;

        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
                email = jwtService.extractUsername(jwt);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}
