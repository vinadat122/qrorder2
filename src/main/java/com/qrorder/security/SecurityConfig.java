package com.qrorder.security;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .exceptionHandling(ex -> ex

                        .authenticationEntryPoint(

                                (request,
                                 response,
                                 authException) ->

                                        response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED,
                                                "Unauthorized"
                                        )
                        )

                        .accessDeniedHandler(

                                (request,
                                 response,
                                 accessDeniedException) ->

                                        response.sendError(
                                                HttpServletResponse.SC_FORBIDDEN,
                                                "Forbidden"
                                        )
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC

                        .requestMatchers(
                                "/auth/login",
                                "/auth/register"
                        )
                        .permitAll()

                        // CUSTOMER

                        .requestMatchers(
                                "/customer/**"
                        )
                        .permitAll()

                        // RESERVATION

                        .requestMatchers(
                                HttpMethod.POST,
                                "/tables/*/reserve"
                        )
                        .permitAll()

                        // ADMIN

                        .requestMatchers(
                                "/dashboard/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/users/**"
                        )
                        .hasRole("ADMIN")

                        // TABLE MANAGEMENT

                        .requestMatchers(
                                HttpMethod.POST,
                                "/tables"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/tables/*/reset"
                        )
                        .hasRole("ADMIN")

                        // CATEGORY

                        .requestMatchers(
                                "/categories/**"
                        )
                        .hasRole("ADMIN")

                        // FOOD

                        .requestMatchers(
                                HttpMethod.POST,
                                "/foods"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/foods/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/foods/**"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/foods/**"
                        )
                        .permitAll()

                        // WAITER

                        .requestMatchers(
                                HttpMethod.GET,
                                "/tables"
                        )
                        .hasAnyRole(
                                "WAITER",
                                "ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/tables/*/checkin"
                        )
                        .hasAnyRole(
                                "WAITER",
                                "ADMIN"
                        )

                        .requestMatchers(
                                "/orders/items/*/served",
                                "/orders/items/*/cancel",
                                "/orders/items/*/wasted"
                        )
                        .hasAnyRole(
                                "WAITER",
                                "ADMIN"
                        )

                        // KITCHEN

                        .requestMatchers(
                                "/orders/items/*/preparing",
                                "/orders/items/*/done"
                        )
                        .hasAnyRole(
                                "KITCHEN",
                                "ADMIN"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/orders/**"
                        )
                        .hasAnyRole(
                                "KITCHEN",
                                "WAITER",
                                "ADMIN"
                        )

                        // CASHIER

                        .requestMatchers(
                                "/payments/**"
                        )
                        .hasAnyRole(
                                "CASHIER",
                                "ADMIN"
                        )

                        // FEEDBACK

                        .requestMatchers(
                                HttpMethod.POST,
                                "/feedbacks"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/feedbacks"
                        )
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(

                        jwtFilter,

                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}