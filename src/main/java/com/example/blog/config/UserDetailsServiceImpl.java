package com.example.blog.config;

import com.example.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=== LOGIN ATTEMPT: " + email + " ===");

        return userRepository.findByEmail(email)
                .map(user -> {
                    System.out.println("=== USER FOUND: " + user.getEmail() + " ===");
                    return User.withUsername(user.getEmail())
                            .password(user.getPassword())
                            .roles("USER")
                            .build();
                })
                .orElseThrow(() -> {
                    System.out.println("=== USER NOT FOUND: " + email + " ===");
                    return new UsernameNotFoundException("User not found: " + email);
                });
    }
}