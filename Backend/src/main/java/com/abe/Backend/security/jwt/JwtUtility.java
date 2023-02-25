package com.abe.Backend.security.jwt;

import com.abe.Backend.security.serviceImlp.UserDetailsImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtility {
//    @Value("${jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${jwtExpirationMs}")
//    private long jwtExpirationMs;
//
//    @Value("${jwtCookieName}")
//    private String jwtCookieName;
//
//    public String generateTokenFromUserName(String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//    }
//    public boolean validateJwtToken(String authToken, UserDetails userDetails) {
//        final String username = getUserNameFromJwtToken(authToken);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
//    }
//
//
//    public String getJwtFromCookies(HttpServletRequest request) {
//        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
//        if (cookie != null) {
//            return cookie.getValue();
//        } else {
//            return null;
//        }
//    }
/////maybe needs impl
//    public ResponseCookie generateJwtCookie(UserDetails userPrincipal) {
//        String jwt = generateTokenFromUserName(userPrincipal.getUsername());
//        return ResponseCookie.from(jwtCookieName, jwt).path("/api").maxAge(24L * 60 * 60).httpOnly(true).build();
//    }
//
//    public ResponseCookie getCleanJwtCookie() {
//        return ResponseCookie.from(jwtCookieName, null).path("/api").build();
//    }
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//    public String getUserNameFromJwtToken(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date getExpirationFromJwtToken(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//    private Boolean isTokenExpired(String token) {
//        return getExpirationFromJwtToken(token).before(new Date());
//    }

}

