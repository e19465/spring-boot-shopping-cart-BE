package com.sasindu.shoppingcart.security.jwt;

import com.sasindu.shoppingcart.helpers.HelperUtilStaticMethods;
import com.sasindu.shoppingcart.models.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {
    @Value("${jwt.access.secret}")
    private String accessTokenSecret;

    @Value("${jwt.access.expiration.minutes}")
    private String accessTokenExpirationMinutes;

    @Value("${jwt.refresh.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh.expiration.days}")
    private String refreshTokenExpirationDays;


    /**
     * Get the Access Token for the given user
     *
     * @param appUser user
     * @return access token
     */
    public String generateAccessToken(AppUser appUser) {
        try {
            Map<String, Object> claims = Map.of(
                    "userId", appUser.getId(),
                    "username", appUser.getUsername(),
                    "roles", List.of(appUser.getRole())
            );
            long expire = Long.parseLong(accessTokenExpirationMinutes) * 60 * 1000; // minutes to milliseconds
            String subject = appUser.getUsername();
            return generateToken(subject, claims, expire, accessKey());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Get the Refresh Token for the given user
     *
     * @param appUser user
     * @return refresh token
     */
    public String generateRefreshToken(AppUser appUser) {
        try {
            Map<String, Object> claims = Map.of(
                    "userId", appUser.getId()
            );
            long expire = Long.parseLong(refreshTokenExpirationDays) * 24 * 60 * 60 * 1000; // days to milliseconds
            String subject = appUser.getId().toString();
            return generateToken(subject, claims, expire, refreshKey());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Validate the given access token
     *
     * @param token access token
     * @return if valid -> true, else -> false
     */
    public boolean isAccessTokenValid(String token) {
        try {
            return isTokenValid(token, accessKey());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Validate the given refresh token
     *
     * @param token refresh token
     * @return if valid -> true, else -> false
     */
    public boolean isRefreshTokenValid(String token) {
        try {
            return isTokenValid(token, refreshKey());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Validate the given access token and get the email
     *
     * @param token access token
     * @return email
     */
    public String getEmailFromAccessToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token, accessKey());
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Validate the given refresh token and get the user id
     *
     * @param token refresh token
     * @return user id in String
     */
    public Long getUserIdFromRefreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token, refreshKey());
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //! PRIVATE METHODS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

    /**
     * Generate the token with the given subject, claims, expire, and secret
     *
     * @param subject subject of the token
     * @param claims  claims of the token
     * @param expire  expire time of the token
     * @param secret  secret key of the token
     * @return token
     */
    private String generateToken(String subject, Map<String, Object> claims, long expire, SecretKey secret) {
        try {
            return Jwts.builder()
                    .subject(subject)
                    .claims(claims)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expire))
                    .signWith(secret)
                    .compact();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Generate the secret key for the access token
     *
     * @return SecretKey
     */
    private SecretKey accessKey() {
        return Keys.hmacShaKeyFor(HelperUtilStaticMethods.hexStringToByteArray(accessTokenSecret));
    }


    /**
     * Generate the secret key for the refresh token
     *
     * @return SecretKey
     */
    private SecretKey refreshKey() {
        return Keys.hmacShaKeyFor(HelperUtilStaticMethods.hexStringToByteArray(refreshTokenSecret));
    }


    /**
     * Validate the given token with the secret key
     *
     * @param token  token
     * @param secret secret key
     * @return if valid -> true, else -> false
     */
    private boolean isTokenValid(String token, SecretKey secret) {
        try {
            Jwts.parser().verifyWith(secret).build().parse(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    /**
     * Get the claims from the given token
     *
     * @param token  token
     * @param secret secret key
     * @return claims
     */
    private Claims getClaimsFromToken(String token, SecretKey secret) {
        try {
            return Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
