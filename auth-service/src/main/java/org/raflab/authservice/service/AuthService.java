package org.raflab.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raflab.authservice.dto.LoginDTO;
import org.raflab.authservice.dto.RoleReqDTO;
import org.raflab.authservice.dto.UserDTO;
import org.raflab.authservice.errors.ConflictException;
import org.raflab.authservice.errors.ForbiddenException;
import org.raflab.authservice.errors.NotAuthorizedException;
import org.raflab.authservice.errors.NotFoundException;
import org.raflab.authservice.mapper.UserMapper;
import org.raflab.authservice.model.Role;
import org.raflab.authservice.model.User;
import org.raflab.authservice.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KeyService keyService;

    @Transactional
    public UserDTO createUser(UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setHashedPassword(hashedPassword);

        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.GUEST);

        try {
            return userMapper.toResponseDTO(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Email is already in use");
        }
    }

    @Transactional
    public String loginUser(LoginDTO userData){
        String email = userData.getEmail();
        String password = userData.getPassword();

        User user = userRepository.getUserByEmail(email).orElseThrow(NotAuthorizedException::new);

        if (!passwordEncoder.matches(password, user.getHashedPassword()))
            throw new NotAuthorizedException();

        String key = keyService.generateAndSaveApiKey(user);

        log.info("USER LOGIN: {}", user.getId());

        return key;
    }

    public UserDTO validateKey(String key) {
        return userMapper.toResponseDTO(keyService.getUserByKey(key));
    }

    @Transactional
    public void logoutUser(String apiKey) {
        User user = keyService.getUserByKey(apiKey);

        log.info("USER LOGOUT: {}", user.getId());

        keyService.deleteKey(apiKey);
    }

    @Transactional
    public UserDTO changeRoles(RoleReqDTO request, String apiKey) {
        User initiator = keyService.getUserByKey(apiKey);
        if (!initiator.getRoles().contains(Role.ADMIN)) throw new ForbiddenException();

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (initiator.getId().equals(user.getId())) throw new ForbiddenException("Admin cannot change their own role");

        user.setRoles(request.getRoles());

        return userMapper.toResponseDTO(userRepository.save(user));
    }
}
