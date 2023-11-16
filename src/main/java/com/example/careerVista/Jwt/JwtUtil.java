package com.example.careerVista.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtil {
    private String secret ="backend";
    public String extractUsername(String token){
        return this.extractClaims(token,Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return this.extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token , Function<Claims,T>claimsResolver){
        final  Claims claims = this. extractAllClaims(token);
        log.info(String.valueOf(claims));
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims (String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    public  Boolean isTokenExpired(String token){
        return this.extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return(username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }

    public void getAuthentication(String token){
        Claims claims = this.extractAllClaims(token);
        String username=claims.getSubject();
        String role= (String) claims.get("role");
        System.out.println(username+" "+role);
    }


    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()

                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*10)))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String generateToken(String username,String role){
        Map<String,Object> claims =new HashMap<>();
        claims.put("role",role);
        return this.createToken(claims,username);
    }

}
