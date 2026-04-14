package org.raflab.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.raflab.authservice.errors.NotAuthorizedException;
import org.raflab.authservice.model.ApiKey;
import org.raflab.authservice.model.User;
import org.raflab.authservice.repository.ApiKeyRepository;
import org.raflab.authservice.utils.KeyGenerator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeyService {
    private final ApiKeyRepository apiKeyRepository;

    @Transactional
    public String generateAndSaveApiKey(User user) {
        String rawKey = KeyGenerator.generateNewKey();

        String hashedKey = DigestUtils.sha256Hex(rawKey);

        ApiKey apiKey = new ApiKey();
        apiKey.setHashedKey(hashedKey);
        apiKey.setUser(user);
        apiKeyRepository.save(apiKey);

        return rawKey;
    }

    @Transactional
    public User getUserByKey(String rawKey) {
        String hashedKey = DigestUtils.sha256Hex(rawKey);

        ApiKey apiKey = apiKeyRepository.getApiKeyByHashedKey(hashedKey).orElseThrow(NotAuthorizedException::new);

        return apiKey.getUser();
    }

    public void deleteKey(String rawKey) {
        String hashedKey = DigestUtils.sha256Hex(rawKey);

        apiKeyRepository.deleteApiKeyByHashedKey(hashedKey);
    }
}
