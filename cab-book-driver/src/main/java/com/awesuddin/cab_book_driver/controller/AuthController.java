package com.awesuddin.cab_book_driver.controller;

import com.awesuddin.cab_book_driver.dto.AuthRequest;
import com.awesuddin.cab_book_driver.dto.AuthResponse;
import com.awesuddin.cab_book_driver.entity.Driver;
import com.awesuddin.cab_book_driver.repository.DriverRepository;
import com.awesuddin.cab_book_driver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final DriverRepository driverRepository;

    // ✅ Register (No token return, only success message)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        authService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Driver registered successfully");
    }

    // ✅ Login (returns token + driver info)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());

        Driver driver = driverRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return ResponseEntity.ok(
                new AuthResponse(token, driver.getId(), driver.getUsername())
        );
    }
}
