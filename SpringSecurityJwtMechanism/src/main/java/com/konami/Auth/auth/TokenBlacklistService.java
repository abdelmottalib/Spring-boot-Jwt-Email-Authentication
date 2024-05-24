package com.konami.Auth.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class TokenBlacklistService {
    private final BlackListRepository blackListRepository;

    public void blacklistToken(String token) {
        BlacklistToken blacklistToken = BlacklistToken.builder().token(token).build();
        blackListRepository.save(blacklistToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackListRepository.existsByToken(token);
    }
}
