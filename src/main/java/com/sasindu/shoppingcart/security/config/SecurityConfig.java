package com.sasindu.shoppingcart.security.config;

import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.security.jwt.JWTAuthEntryPoint;
import com.sasindu.shoppingcart.security.jwt.JWTAuthFilter;
import com.sasindu.shoppingcart.security.services.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final AppUserDetailsService _appUserDetailsService;
    private final JWTAuthEntryPoint _jwtAuthEntryPoint;


    /**
     * Cookie Same Site Supplier - set to strict
     *
     * @return cookie same site supplier
     */
    @Bean
    public CookieSameSiteSupplier cookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict();
    }


    /**
     * CORS Configuration
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ApplicationConstants.getCorsAllowedOrigins());
        configuration.setAllowedMethods(ApplicationConstants.getCorsAllowedMethods());
        configuration.setAllowedHeaders(ApplicationConstants.getCorsAllowedHeaders());
        configuration.setAllowCredentials(ApplicationConstants.isCorsAllowCredentials());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     * Password encoder bean - use BCryptPasswordEncoder
     *
     * @return PasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        try {
            return new BCryptPasswordEncoder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * JWT Auth Filter
     *
     * @return JWTAuthFilter object
     */
    public JWTAuthFilter jwtAuthFilter() {
        return new JWTAuthFilter();
    }


    /**
     * Authentication manager bean
     *
     * @param authConfig the AuthenticationConfiguration object
     * @return AuthenticationManager object
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        try {
            return authConfig.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * DaoAuthenticationProvider bean
     *
     * @return DaoAuthenticationProvider object
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        try {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(_appUserDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Security filter chain bean
     *
     * @param httpSecurity the HttpSecurity object
     * @return SecurityFilterChain object
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    .cors(Customizer.withDefaults())
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(req ->
                            req.requestMatchers(ApplicationConstants.PUBLIC_URLS).permitAll()
                                    .anyRequest().authenticated()
                    )
                    .exceptionHandling(ex -> ex.authenticationEntryPoint(_jwtAuthEntryPoint))
                    .authenticationProvider(daoAuthenticationProvider())
                    .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
