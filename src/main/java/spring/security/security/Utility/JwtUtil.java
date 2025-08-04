package spring.security.security.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "ONGODWETRUSTALLOTHERSWEHACK@THISISXKEYSCORE";
    private final SecretKey keys = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateJwtToken(String userName) {


        long EXPIRATION_TIME = 1000 * 60 *60; // 1 hour

        return Jwts.builder().
                setSubject(userName).
                setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).
                signWith(keys, SignatureAlgorithm.HS256).
                compact();

    }

    public String extractToken(String token) {

        return getClaims(token).getSubject(); //Here why we are returning subject because inside token the
        //username is placed in subject
    }

    private Claims getClaims(String token) {
        Claims body = Jwts.parserBuilder(). // Start parser
                setSigningKey(keys).// Set key to verify signature
                setAllowedClockSkewSeconds(60).//If the token expired at 10:00:00, but your server time is 10:00:50, it will still treat the token as valid
                build(). // Build the parser
                parseClaimsJws(token).// Parse and validate token
                getBody(); // Get claims from token
        return body;
    }

    public boolean validateToken(String userName, UserDetails userDetails, String token) {


        return userName.equals(userDetails.getUsername()) && isTokenValid(token);
    }

    private boolean isTokenValid(String token) {

        return getClaims(token).getExpiration().after(new Date());
    }
}
