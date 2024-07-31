package tuorjp.imageliteapi.application.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tuorjp.imageliteapi.domain.AccessToken;
import tuorjp.imageliteapi.domain.entity.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final SecretKeyGenerator keyGenerator;

    public AccessToken generateToken(User user) {
        var key = keyGenerator.getKey();
        var expirationDate = generatedExpirationDate();
        var claims = generateTokenClaims(user);

        String token = Jwts
                .builder()
                .signWith(key)
                .subject(user.getEmail())
                .expiration(expirationDate)
                .claims(claims)
                .compact();

        return new AccessToken(token);
    }

    private Date generatedExpirationDate() {
        var expirationMinutes = 60;

        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());

        return claims;
    }

    public String getEmailFromToken(String token) {
        try {
            JwtParser build = Jwts
                    .parser()
                    .verifyWith(keyGenerator.getKey())
                    .build();

            Jws<Claims> jwsClaims = build.parseSignedClaims(token);

            Claims claims = jwsClaims.getPayload();

            return claims.getSubject();
        } catch (JwtException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }
}
