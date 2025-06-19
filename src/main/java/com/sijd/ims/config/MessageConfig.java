package com.sijd.ims.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.text.MessageFormat;

@Configuration
@PropertySource("classpath:messages.properties")
public class MessageConfig {

    @Autowired
    private Environment environment;

    public String getMessage(String messageKey, Object... args) {
        String message = environment.getProperty(messageKey);
        if (message != null && args != null) {
            return MessageFormat.format(message, args);
        }
        return message;
    }
}
