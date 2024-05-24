package com.konami.Auth.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlacklistToken, Integer> {
    boolean existsByToken(String token);
}
