package com.example.leavemanagement.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final Map<String, UserDetails> users = new HashMap<>();

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        logger.info("UserService initialized with passwordEncoder: {}", passwordEncoder.getClass().getSimpleName());
    }

    @PostConstruct
    public void init() {
        try {
            // Create admin user
            String adminPassword = passwordEncoder.encode("admin123");
            logger.debug("Creating admin user with encoded password");
            UserDetails adminUser = User.builder()
                    .username("admin@example.com")
                    .password(adminPassword)
                    .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_MANAGER"))
                    .build();
            users.put("admin@example.com", adminUser);
            logger.info("Admin user created successfully: {}", adminUser.getUsername());

            // Create employee user
            String employeePassword = passwordEncoder.encode("admin12");
            logger.debug("Creating employee user with encoded password");
            UserDetails employeeUser = User.builder()
                    .username("john@example.com")
                    .password(employeePassword)
                    .authorities(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
                    .build();
            users.put("john@example.com", employeeUser);
            logger.info("Employee user created successfully: {}", employeeUser.getUsername());
            
            // Log all users and their roles
            users.forEach((email, user) -> {
                String roles = user.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(", "));
                logger.info("Initialized user - Email: {}, Roles: {}", email, roles);
            });
        } catch (Exception e) {
            logger.error("Error initializing users", e);
            throw new RuntimeException("Failed to initialize users", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user by username: {}", username);
        UserDetails user = users.get(username);
        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        String roles = user.getAuthorities().stream()
            .map(auth -> auth.getAuthority())
            .collect(Collectors.joining(", "));
        logger.info("User found - Username: {}, Roles: {}", username, roles);
        
        return user;
    }
} 