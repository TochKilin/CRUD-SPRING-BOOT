package myspring.com.myspring.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    private final SecretKey key;
    private final long expirationMs;
    private final long refreshExpirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs,
            @Value("${app.jwt.refresh-expiration-ms}") long refreshExpirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), TYPE_ACCESS, expirationMs);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), TYPE_REFRESH, refreshExpirationMs);
    }

    // នៅទុកបណ្តោះអាសន្ន ដើម្បីកុំឱ្យ AuthService ខូច រហូតដល់ជំហាន 3
    public String generateToken(UserDetails userDetails) {
        return generateAccessToken(userDetails);
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // ប្រើក្នុង JwtAuthFilter: ទទួលតែ access token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return isValid(token, userDetails, TYPE_ACCESS);
    }

    // ប្រើពេល refresh: ទទួលតែ refresh token
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return isValid(token, userDetails, TYPE_REFRESH);
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public long getRefreshExpirationMs() {
        return refreshExpirationMs;
    }

    private String buildToken(String username, String type, long ttlMs) {
        Date now = new Date();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(username)
                .claim(CLAIM_TYPE, type)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ttlMs))
                .signWith(key)
                .compact();
    }

    private boolean isValid(String token, UserDetails userDetails, String expectedType) {
        Claims claims = parseClaims(token);
        return expectedType.equals(claims.get(CLAIM_TYPE, String.class))
                && claims.getSubject().equals(userDetails.getUsername())
                && claims.getExpiration().after(new Date());
    }

    // បោះ JwtException បើ token ខុស ឬផុតកំណត់
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}