package com.abe.Backend.security;

import com.abe.Backend.repository.UserRepository;

import com.abe.Backend.security.serviceImpl.UserDetailsServiceImpl;
import com.abe.Backend.security.session.AuthEntryPointSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableSpringHttpSession
public class WebSecurityConfiguration {

    private final UserRepository userRepository;
    private final ConfigurableBeanFactory beanFactory;
    private final AuthEntryPointSession unauthorizedHandler;

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var chain = http
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/api/user/sign-in").permitAll()
                        .requestMatchers("/api/user/csrf").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll())
                //adding
                .sessionManagement(customizer -> customizer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(2))
                .authenticationProvider(authenticationProvider())
//
                .csrf(customizer -> customizer
                        .ignoringRequestMatchers(toH2Console()))
                .headers(customizer -> customizer
                        .frameOptions().sameOrigin())
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(unauthorizedHandler))
                .rememberMe(customizer -> customizer.alwaysRemember(true).key("demo").userDetailsService(userDetailsService()))
                .build();

        var rememberMeServices = http.getSharedObject(RememberMeServices.class);
        beanFactory.registerSingleton("rememberMeServices", rememberMeServices);

        return chain;
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

    @Bean
    CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("CUSTOMSESSIONID");
        defaultCookieSerializer.setUseHttpOnlyCookie(true);
        defaultCookieSerializer.setUseSecureCookie(true);
        defaultCookieSerializer.setSameSite("Strict");
        return defaultCookieSerializer;
    }



}
