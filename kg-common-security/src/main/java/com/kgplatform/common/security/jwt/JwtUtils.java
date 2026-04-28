package com.kgplatform.common.security.jwt;

import com.kgplatform.common.security.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JwtUtils
 * <p>
 * JwtUtils业务类
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
public class JwtUtils {

    private final SecretKey secretKey;
    private final long expireSeconds;

    public JwtUtils(String secret, long expireSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireSeconds = expireSeconds;
    }

    public String createToken(Long userId, String username) {
        return createToken(userId, username, null, null);
    }

    public String createToken(Long userId, String username, String nickname, Long tenantId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("nickname", nickname)
                .claim("tenantId", tenantId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireSeconds * 1000))
                .signWith(secretKey)
                .compact();
    }

    public LoginUser parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long userId = Long.valueOf(claims.getSubject());
        String username = claims.get("username", String.class);
        String nickname = claims.get("nickname", String.class);
        Object tenantIdValue = claims.get("tenantId");
        Long tenantId = tenantIdValue == null ? null : Long.valueOf(String.valueOf(tenantIdValue));
        return new LoginUser(userId, username, nickname, tenantId);
    }
}
