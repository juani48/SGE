package com.bsoftware.sge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bsoftware.sge.service.CustomUserDetailsService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login", 
                    "/register", 
                    "/api/register", 
                    "/api/login", 
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN") 

                //.requestMatchers("/file/create/**", "/api/file/create/**").hasRole("CREATE_FILE")
                //.requestMatchers("/file/update/**", "/api/file/update/**").hasRole("EDIT_FILE")
                //.requestMatchers("/file/delete/**", "/api/file/delete/**").hasRole("DELETE_FILE")

                //.requestMatchers("/procedure/create/**", "/api/procedure/create/**").hasRole("CREATE_PROCEDURE")
                //.requestMatchers("/procedure/update/**", "/api/procedure/update/**").hasRole("EDIT_PROCEDURE")
                //.requestMatchers("/procedure/delete/**", "/api/procedure/delete/**").hasRole("DELETE_PROCEDURE")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // página de login personalizada
                .loginProcessingUrl("/login") // URL donde se envía el formulario
                .defaultSuccessUrl("/", true) // redirige a inicio tras éxito
                .failureUrl("/login?error=true") // redirige con error
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1) // una sesión por usuario
            );

        return http.build();
    }
}