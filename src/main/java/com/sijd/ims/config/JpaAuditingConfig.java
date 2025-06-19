package com.sijd.ims.config;

import org.springframework.context.annotation.Configuration;


import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JpaAuditingConfig {

//    @Bean
//    public AuditorAware<String> auditorAwareImpl() {
//        return new AuditorAwareImpl();
//    }
}
