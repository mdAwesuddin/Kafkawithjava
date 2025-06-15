package com.awesuddin.cab_book_driver.service;

import com.awesuddin.cab_book_driver.entity.Driver;
import com.awesuddin.cab_book_driver.repository.DriverRepository;
import com.awesuddin.cab_book_driver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final DriverRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(String username, String password) {
        if (repo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Driver driver = Driver.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        repo.save(driver);
        return jwtUtil.generateToken(username);
    }

    public String login(String username, String password) {
        Driver driver = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, driver.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(username);
    }
}
