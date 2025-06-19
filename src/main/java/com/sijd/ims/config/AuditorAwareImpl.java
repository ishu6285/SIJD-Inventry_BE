package com.sijd.ims.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Get current user from Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            authentication.isAuthenticated();
            return Optional.ofNullable(authentication.getName());
        }
        return Optional.of("SYSTEM");

        // Return username or user ID
    }
}
