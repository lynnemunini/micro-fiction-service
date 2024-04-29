package com.grayseal.microfictionapi.controller;

import com.grayseal.microfictionapi.model.User;
import com.grayseal.microfictionapi.model.UserRegistrationRequest;
import com.grayseal.microfictionapi.repository.UserRepository;
import com.grayseal.microfictionapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.grayseal.microfictionapi.Utils.isValidRegistrationRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/register")
    private ResponseEntity<String> createUser(@RequestBody UserRegistrationRequest registrationRequest) {

        if (!isValidRegistrationRequest(registrationRequest)) {
            return ResponseEntity.badRequest().body("Invalid registration request");
        }
        // Validate user data (e.g., email format, password strength)
        User existingUser = userRepository.findByEmail(registrationRequest.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (userService.registerUser(registrationRequest)) {
            return ResponseEntity.ok("User created successfully");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}
