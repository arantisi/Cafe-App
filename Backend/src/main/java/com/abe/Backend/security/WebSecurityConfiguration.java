package com.abe.Backend.security;

import com.abe.Backend.repository.UserRepository;
import com.abe.Backend.security.jwt.AuthEntryPointJwt;
import com.abe.Backend.security.jwt.AuthTokenFilter;
import com.abe.Backend.security.serviceImlp.UserDetailsServiceImpl;
import com.abe.Backend.security.session.SessionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;
    private final AuthTokenFilter authTokenFilter;
    private final SessionFilter sessionFilter;
    private final UserRepository userRepository;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/user/**","/api/test/**").permitAll()
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers("/products/**")
                .authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.cors().and().csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/auth/**","/api/test/**",h2ConsolePath + "/**").permitAll()
//                .requestMatchers(toH2Console()).permitAll()
//                .and().headers().frameOptions().sameOrigin()
//
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/products/**")
//                .authenticated().and()
////                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .headers().frameOptions().sameOrigin()
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
}
