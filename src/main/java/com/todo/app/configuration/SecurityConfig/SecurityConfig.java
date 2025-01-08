package com.todo.app.configuration.SecurityConfig;

import com.todo.app.configuration.appConfig.JwtFilterConfiguration;
import com.todo.app.exception.AuthenticationExceptionHandler;
import com.todo.app.exception.SecurityAccessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final SecurityAccessDeniedHandler accessDeniedHandler;
    private final CorsConfig corsConfiguration;
    private final JwtFilterConfiguration jwtFilterConfiguration;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
                .requestMatchers(
                      HttpMethod.POST, "/todo/add-todo").authenticated()
                 .requestMatchers(HttpMethod.GET, "/todo/get-todos").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/todo/delete-todo/*").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/todo/edit-todo").authenticated()
                                .anyRequest().authenticated()
                )

                .exceptionHandling(request -> {
                    request.authenticationEntryPoint(authenticationExceptionHandler);
                    request.accessDeniedHandler(accessDeniedHandler);
                })
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilterConfiguration, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfiguration));
        return httpSecurity.build();
    }
}
