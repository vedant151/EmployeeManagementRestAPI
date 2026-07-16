package com.ZestAssignment.EmployeeManagment.config;

import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtFilterChain jwtFilterChain;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No HTTP session
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/login", "/api/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/login", "/api/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/req/signup/**", "/req/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/home").permitAll()

                        // organizer only
                        .requestMatchers(HttpMethod.GET,   "/api/getEmployee/").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.PUT,    "/api/event/**").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.POST,   "/api/createEmployee").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.DELETE, "/api/delete/**").hasRole("ORGANIZER")

                        // employee only
                        .requestMatchers(HttpMethod.GET, "/api/EmployeeId/**").hasRole("EMPLOYEE")



                        .requestMatchers(HttpMethod.POST, "/api/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/role").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                         jwtFilterChain, UsernamePasswordAuthenticationFilter.class
                );


        return httpSecurity.build();

    }

}
