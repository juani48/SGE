package com.bsoftware.sge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bsoftware.sge.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
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
                
                .requestMatchers(
                    "/admin/**"
                ).hasRole("ADMIN") // ejemplo de restricción por rol

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // pagina de login
                .loginProcessingUrl("/api/login") // endpoint para procesar login
                .defaultSuccessUrl("/", true) // redirige a la raíz después del login exitoso
                .successHandler((req, res, authentication) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                })
                .failureHandler((req, res, exception) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1) // opcional: una sesión por usuario
            );
            //.csrf(csrf -> csrf.disable()); // si consumís la API desde un front separado (SPA); en apps monolíticas con formularios, mejor dejarlo activo

        return http.build();
    }
}