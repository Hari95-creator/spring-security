package spring.security.security.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "ONGODWETRUSTALLOTHERSWEHACK@THISISXKEYSCORE";
    private final SecretKey keys = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateJwtToken(String userName) {


        long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(keys, SignatureAlgorithm.HS256)
                .compact();

    }
}
