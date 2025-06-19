package com.sijd.ims.service.impl;

import com.sijd.ims.dto.user.LoginRequest;
import com.sijd.ims.dto.user.RefreshTokenRequest;
import com.sijd.ims.dto.user.RegistrationRequest;
import com.sijd.ims.entity.user.User;
import com.sijd.ims.exception.SijdException;
import com.sijd.ims.repository.user.UserRepository;
import com.sijd.ims.response.APIResponse;
import com.sijd.ims.response.AuthResponse;
import com.sijd.ims.utility.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private  AuthenticationManager authenticationManager;
    public APIResponse<AuthResponse> register(RegistrationRequest request) {

        User newUser = userRepository.save(buildUser(request));

        return APIResponse.success(AuthResponse.builder()
                .user(newUser)
                .message("user registration success")
                .build());
    }

    private User buildUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .email(request.getEmail())
                .isActive(true)
                .nickName(request.getNickName())
                .userType(request.getUserType())
                .effectiveDate(LocalDateTime.now())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    public APIResponse<AuthResponse> login(LoginRequest request) throws SijdException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail());
        var jwt = jwtUtils.generateToken(user);
        var userRole = userRepository.findByEmail(request.getEmail()).getUserType();
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        return APIResponse.success(AuthResponse.builder()
                        .token(jwt)
                        .refreshToken(refreshToken)
                        .expirationTime("24h")
                        .statusCode(200)
                        .message("login success")
                        .role(String.valueOf(userRole))
                .build());
    }

    public APIResponse<AuthResponse> refresh(RefreshTokenRequest request) {
        String email = jwtUtils.extractUsername(request.getToken());
        User user =userRepository.findByEmail(email);
        var userRole = userRepository.findByEmail(email).getUserType();
        if (jwtUtils.isTokenValid(request.getToken(),user)){
            var jwt = jwtUtils.generateToken(user);
            return APIResponse.success(AuthResponse.builder()
                    .token(jwt)
                    .token(jwtUtils.generateRefreshToken(new HashMap<>(), user))
                    .message("successfully Refreshed Token")
                    .expirationTime("24h")
                    .role(String.valueOf(userRole))
                    .build());

        }
        return APIResponse.error("invalid token");
    }
}
