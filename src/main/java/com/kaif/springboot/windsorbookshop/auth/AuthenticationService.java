package com.kaif.springboot.windsorbookshop.auth;

import com.kaif.springboot.windsorbookshop.config.JwtService;
import com.kaif.springboot.windsorbookshop.repo.UserRepository;
import com.kaif.springboot.windsorbookshop.service.EmailService;
import com.kaif.springboot.windsorbookshop.user.Role;
import com.kaif.springboot.windsorbookshop.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService; // <- Inject EmailService

    public AuthenticationResponse register(RegisterRequest request) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        // Generate verification code
        String verificationCode = generateVerificationCode();

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .verificationCode(verificationCode)
                .isEmailVerified(false) // Not verified yet
                .build();

        // Save unverified user
        userRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);

        return AuthenticationResponse.builder()
                .message("Registration successful. Please verify your email.")
                .build();
    }

    public AuthenticationResponse verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEmailVerified()) {
            return AuthenticationResponse.builder()
                    .message("Email is already verified.")
                    .build();
        }

        if (user.getVerificationCode().equals(code)) {
            user.setEmailVerified(true);
            user.setVerificationCode(null); // Optional: clear code
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .role(user.getRole().name())
                    .message("Email verified successfully.")
                    .build();
        } else {
            userRepository.delete(user); // Optional: delete user if verification fails
            return AuthenticationResponse.builder()
                    .message("Invalid verification code.")
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEmailVerified()) {
            return AuthenticationResponse.builder()
                    .message("Please verify your email before logging in.")
                    .build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }

    public AuthenticationResponse updateUser(int id, RegisterRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Updating user: " + request.getFirstname() + ", " + request.getLastname() + ", " + request.getEmail());

        if (request.getFirstname() != null) {
            user.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null) {
            user.setLastname(request.getLastname());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }



    // Method to delete unverified user
    public boolean deleteUnverifiedUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.isEmailVerified()) {
                // Delete the user if not verified
                userRepository.delete(user);
                return true;
            }
        }
        return false;
    }


    // 1. Send reset link
    public AuthenticationResponse sendResetPasswordLink(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return AuthenticationResponse.builder().message("Email not found.").build();
        }

        User user = userOptional.get();

        String resetToken = generateVerificationCode(); // You can use UUID instead
        user.setResetToken(resetToken);
        userRepository.save(user);

        String resetLink = "http://localhost:63342/front/frontEnd/resetpassword.html?email=" + user.getEmail() + "&token=" + resetToken;
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);

        return AuthenticationResponse.builder().message("Reset link sent to email.").build();
    }

    public AuthenticationResponse resetPassword(ResetPasswordRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return AuthenticationResponse.builder().message("User not found.").build();
        }

        User user = userOptional.get();

        if (!request.getToken().equals(user.getResetToken())) {
            return AuthenticationResponse.builder().message("Invalid or expired token.").build();
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return AuthenticationResponse.builder().message("Password cannot be empty.").build();
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setResetToken(null); // Clear token after use
        userRepository.save(user);

        return AuthenticationResponse.builder().message("Password has been reset successfully.").build();
    }



}
