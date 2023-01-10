package com.goodmortician.notes.config;

import com.goodmortician.notes.auth.AuthHandlerInterceptor;
import com.goodmortician.notes.auth.JwtAuthenticationEntryPoint;
import com.goodmortician.notes.auth.JwtTokenUtil;
import com.goodmortician.notes.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return httpSecurity.build();
    }
    @RequiredArgsConstructor
    @Configuration
    public static class WebMvcConfiguration implements WebMvcConfigurer {
        private final AuthHandlerInterceptor authenticateWebInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(authenticateWebInterceptor);
        }
    }
    @Bean
    public AuthHandlerInterceptor authenticateWebInterceptor(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        return new AuthHandlerInterceptor(jwtUserDetailsService, jwtTokenUtil);
    }

}
