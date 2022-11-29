package n.samsonov.newsfeed.security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String buildToken(UUID id) {
        Date tokenValidationPeriod = Date.from(LocalDateTime.now().plusDays(30)
                .atZone(ZoneId.systemDefault()).toInstant());
        return "Bearer " + Jwts.builder()
                .setSubject(String.valueOf(id))
                .setExpiration(tokenValidationPeriod)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getIdFromToken(String token) {
     return Jwts.parser()
              .setSigningKey(secret)
              .parseClaimsJws(clearToken(token))
               .getBody().getSubject();
   }

     public String clearToken(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token; }

    public boolean validateToken(@NonNull String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

}
