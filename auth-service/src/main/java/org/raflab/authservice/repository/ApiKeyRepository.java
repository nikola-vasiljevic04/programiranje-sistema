package org.raflab.authservice.repository;

import org.raflab.authservice.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> getApiKeyByHashedKey(String hashedKey);

    void deleteApiKeyByHashedKey(String hashedKey);
}
