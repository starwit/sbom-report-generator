package de.starwit.sbom;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
@EnableAutoConfiguration(exclude = OAuth2ResourceServerAutoConfiguration.class)
public class DevSecurityConfig {
}