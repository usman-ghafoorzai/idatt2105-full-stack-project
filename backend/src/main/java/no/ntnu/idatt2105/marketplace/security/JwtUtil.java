package no.ntnu.idatt2105.marketplace.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
  private final long expiration = 86400000; // 1 day in milliseconds
  private SecretKey key;
  private JwtParser jwtParser;

  @PostConstruct
  public void init() {
    // Think it should be like this if saved in application.properties:
    // @Value("${jwt.secret}")
    // private String secretString;
    // TODO: Load this from application.properties or an environment variable
    String secretString = "your-super-secure-and-long-secret-key-should-be-at-least-256-bit";
    byte[] secretBytes = secretString.getBytes(); // OR: use Base64 decoding if you're storing it encoded
    this.key = Keys.hmacShaKeyFor(secretBytes);

    this.jwtParser = Jwts.parser().verifyWith(key).build();
  }

  public String generateToken(String username, String role) {
    return Jwts.builder()
        .subject(username)
        .claim("role", role)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key, Jwts.SIG.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return jwtParser.parseSignedClaims(token).getPayload().getSubject();
  }

  public String extractRole(String token) {
    return jwtParser.parseSignedClaims(token).getPayload().get("role", String.class);
  }

  public boolean validateToken(String token) {
    try {
      jwtParser.parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
