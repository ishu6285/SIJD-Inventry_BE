package com.sijd.ims.controller.application;

import com.sijd.ims.dto.user.LoginRequest;
import com.sijd.ims.dto.user.RefreshTokenRequest;
import com.sijd.ims.dto.user.RegistrationRequest;
import com.sijd.ims.response.APIResponse;
import com.sijd.ims.response.AuthResponse;
import com.sijd.ims.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/${version}/auth/user")
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class UserController {
    private final AuthenticationService authenticationService;
    @PostMapping(value = "/register")
    ResponseEntity<APIResponse<AuthResponse>> register(@Valid @RequestBody RegistrationRequest request){
        APIResponse<AuthResponse> response =authenticationService.register(request);
       return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login")
    ResponseEntity<APIResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request){
        APIResponse<AuthResponse> response =authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<APIResponse<AuthResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest request){
        APIResponse<AuthResponse> response =authenticationService.refresh(request);
        return ResponseEntity.ok(response);
    }



}
