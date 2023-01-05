package com.example.iotassistantrest.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final String serverId;
    private final String serverPassword;

    public WebSecurityConfig(@Value("${local-server.id}") String serverId,
                             @Value("${local-server.password}")String serverPassword) {
        this.serverId = serverId;
        this.serverPassword = serverPassword;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(serverId)
                .password(serverPassword)
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("ADMIN")
                .build());

        return manager;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/","/admin/**").hasRole("ADMIN")
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain mobileFilterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/mobile/**")
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().hasRole("MOBILE")
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain iotFilterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/iot/**")
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().authenticated()
                );
                //.objectPostProcessor();
        return http.build();
    }
}
