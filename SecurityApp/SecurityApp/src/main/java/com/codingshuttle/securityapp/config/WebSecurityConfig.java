package com.codingshuttle.securityapp.config;

import com.codingshuttle.securityapp.filters.JwtAuthFilter;
import com.codingshuttle.securityapp.handlers.OAuth2SuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codingshuttle.securityapp.entities.enums.Permission.*;
import static com.codingshuttle.securityapp.entities.enums.Role.ADMIN;
import static com.codingshuttle.securityapp.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private static final String[] publicRoutes = {"/auth/**", "/home.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(publicRoutes).permitAll()
//                        .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET,"/posts/**").hasAnyAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAnyAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyAuthority(POST_DELETE.name())
                        .anyRequest().authenticated())
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionConfig -> exceptionConfig
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // Handle 403 Forbidden for authenticated users without the required role
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Access Denied: You don't have permission to access this resource.");
                        })) // No explicit AuthenticationEntryPoint needed for OAuth2 login
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2Config->
                        oAuth2Config.failureUrl("/login?error=true")
                                .successHandler(oAuth2SuccessHandler));


//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService getUserDetails(){
//        UserDetails normalUser = User
//                .withUsername("sayan")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
