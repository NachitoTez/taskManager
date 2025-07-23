package com.lemon.taskmanager.user.service;

import com.lemon.taskmanager.exceptions.UserNotFoundException;
import com.lemon.taskmanager.mapper.UserMapper;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.UserRepository;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity findUserEntityByUsername(String username) {
        LOGGER.info("Fetching UserEntity by username '{}'", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    LOGGER.warn("User '{}' not found", username);
                    return new UserNotFoundException(username);
                });
    }

    @Override
    public UserEntity findUserEntityById(UUID id) {
        LOGGER.info("Fetching UserEntity by ID '{}'", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("User #{} not found", id);
                    return new UserNotFoundException(id);
                });
    }

    @Override
    public User findUserById(UUID id) {
        LOGGER.info("Fetching domain User by ID '{}'", id);
        return getDomainUserOrThrow(id, "findUserById");
    }

    @Override
    public User findUserByUsername(String username) {
        LOGGER.info("Fetching domain User by username '{}'", username);
        return userRepository.findByUsername(username)
                .map(userMapper::toDomain)
                .orElseThrow(() -> {
                    LOGGER.warn("User '{}' not found", username);
                    return new UserNotFoundException(username);
                });
    }

    @Override
    public boolean existsByUsername(String username) {
        boolean exists = userRepository.existsByUsername(username);
        LOGGER.info("Checking if username '{}' exists: {}", username, exists);
        return exists;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        LOGGER.info("Saving new UserEntity with username '{}'", userEntity.getUsername());
        return userRepository.save(userEntity);
    }

    private User getDomainUserOrThrow(UUID userId, String contextForLogging) {
        return userRepository.findById(userId)
                .map(userMapper::toDomain)
                .orElseThrow(() -> {
                    LOGGER.warn("User #{} not found during {}", userId, contextForLogging);
                    return new UserNotFoundException(userId);
                });
    }
}
