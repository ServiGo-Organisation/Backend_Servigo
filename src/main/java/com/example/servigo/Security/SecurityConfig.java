package com.example.servigo.Security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private CustomUserDetailsService userDetailsService;

    private static final String FRONTEND_URL = "http://localhost:5173";
    private static final String SECRET_KEY = "9faa372517ac1d389758d3750fc87acf00f542277f26fec1ce4593e93f64e644";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/login", // Autorise ta route de login personnalisée
                                "/auth/login/**",
                                "/api/v1/utilisateur/addUtilisateur",
                                "/oauth2/**", // Pour Google login si jamais nécessaire
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/assets/userProfile/**",
                                "/assets/servicesImages/**",

                                // Routes pour les rôles
                                "/api/v1/roles/prestateurs",
                                "/api/v1/roles/all",
                                "/api/v1/roles/create",
                                "/api/v1/roles/add-to-prestateur",

                                // NOUVELLES ROUTES - Services et Client-Service
                                "/api/v1/services/getAllServices",
                                "/api/v1/client-service/**"
                        ).permitAll()

                        // Routes protégées (nécessitent une authentification)
                        .requestMatchers(
                                "/api/v1/services/addService"  // Création de service protégée
                        ).authenticated()

                        .anyRequest().authenticated()
                )
//                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl(FRONTEND_URL + "/auth/google/callback", true))
                .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(FRONTEND_URL + "/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "SESSION", "XSRF-TOKEN")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://192.168.1.17:19000",  // Expo Go (mobile)
                "http://192.168.1.17:8081",  // Si tu testes le front dans le navigateur
                "http://localhost:5173"       // Pour tests local React/Vite
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(SECRET_KEY.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }
}