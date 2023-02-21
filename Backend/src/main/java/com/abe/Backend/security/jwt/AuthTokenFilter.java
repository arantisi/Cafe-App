package com.abe.Backend.security.jwt;

import com.abe.Backend.security.serviceImlp.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = null;
        String userName = null;
        String jwt = parseJwt(request);
    }
    private String parseJwt(HttpServletRequest request) {
        return jwtUtility.getJwtFromCookies(request);
    }
}
