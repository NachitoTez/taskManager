package com.lemon.taskmanager.auth.service;

import com.lemon.taskmanager.auth.controller.dto.AuthRequest;
import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.auth.controller.dto.EnvironmentResponse;
import com.lemon.taskmanager.auth.controller.dto.RegisterRequest;
import com.lemon.taskmanager.exceptions.UsernameAlreadyTakenException;
import com.lemon.taskmanager.tasks.repository.ProjectRepository;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProjectRepository projectRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);


    public AuthServiceImpl(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            ProjectRepository projectRepository
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.projectRepository = projectRepository;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        UserEntity userEntity = userService.findUserEntityByUsername(request.username());

        if (!userEntity.passwordMatches(request.password(), passwordEncoder)) {
            LOGGER.warn("Login failed for user '{}': invalid credentials", request.username());
            throw new IllegalArgumentException("Invalid credentials");
        }


        String token = jwtService.generateToken(userEntity);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByUsername(request.username())) {
            throw new UsernameAlreadyTakenException(request.username());
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        String rawRole = request.role() != null ? request.role().toUpperCase() : "MEMBER";

        com.lemon.taskmanager.tasks.domain.Role role;
        try {
            role = com.lemon.taskmanager.tasks.domain.Role.valueOf(rawRole);
        } catch (IllegalArgumentException e) {
            //TODO esto hacer una excepcion custom
            throw new IllegalArgumentException("Invalid role: must be 'MEMBER' or 'MANAGER'");
        }

        UserEntity newUserEntity = new UserEntity(request.username(), hashedPassword, role);
        UserEntity savedUserEntity = userService.save(newUserEntity);

        String token = jwtService.generateToken(savedUserEntity);
        return new AuthResponse(token);
    }


    //TODO esto en realidad estaría bueno mandarlo con una MAV pero me parecía que capaz tardaba un poco en implementarlo lindo
    public EnvironmentResponse getEnvironment(User user) {
        List<UUID> projectIds = projectRepository.findAllByMemberId(user.getId())
                .stream()
                .map(ProjectEntity::getId)
                .toList();

        return new EnvironmentResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                projectIds
        );
    }


}
