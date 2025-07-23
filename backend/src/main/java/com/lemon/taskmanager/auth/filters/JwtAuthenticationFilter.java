package com.lemon.taskmanager.auth.filters;

import com.lemon.taskmanager.auth.service.JwtService;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.mapper.UserMapper;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService, UserMapper userMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            LOGGER.warn("Missing or malformed Authorization header");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final String token = authHeader.substring(7);
        final String username;

        try {
            username = jwtService.extractUsername(token);
        } catch (ExpiredJwtException e) {
            LOGGER.warn("Expired token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            LOGGER.warn("Invalid token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userEntity = userService.findUserEntityByUsername(username);
            User user = userMapper.toDomain(userEntity);

            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            LOGGER.info("Authenticated user: {}", username);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth");
    }
}
