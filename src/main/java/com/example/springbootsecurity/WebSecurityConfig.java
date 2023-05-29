package com.example.springbootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.ERROR).permitAll()
                        .requestMatchers("/", "/public").permitAll()
                        //requestMatchers("/", "/public/**").permitAll() // om man vill testa kryptering
                        .requestMatchers("/api/**").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN"))
                //.requestMatchers("/api/**").authenticated())   //om man vill visa user
                //.requestMatchers("/api/**").denyAll())   //om man vill stänga ner
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$CmynNnwTzLU4QQ8XslbY8O0TlT8nIrrss3yGR0JUeTPXC7UPLNXZe")
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$10$CmynNnwTzLU4QQ8XslbY8O0TlT8nIrrss3yGR0JUeTPXC7UPLNXZe")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
