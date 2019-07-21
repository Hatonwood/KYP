package com.localhost.security.jwt;

import com.localhost.entity.User;
import com.localhost.security.services.UserPrinciple;
import com.localhost.service.UserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    /* JWT Secret */
    @Value("${kyp.jwtSecret}")
    private String jwtSecret;

    /* Token Expiration period */
    @Value("${kyp.jwtExpiration}")
    private int jwtExpiration;

    @Autowired
    UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateJwtToken(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrincipal.getUsername()).get();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000)) //to seconds
                .claim("authority",userPrincipal.getAuthorities().iterator().next().getAuthority())
                .claim("userID", user.getUserID())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature from " + getUserNameFromJwtToken(authToken), e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token from " + getUserNameFromJwtToken(authToken), e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token from " + getUserNameFromJwtToken(authToken), e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty", e);
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}
