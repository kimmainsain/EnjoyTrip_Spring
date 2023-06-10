package com.ssafy.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.security.auth.Subject;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    public class JwtResult{
        TOKEN_RESULT result;

        public TOKEN_RESULT getResult() {
            return result;
        }

        public String getId() {
            return id;
        }
        public Boolean getAdmin(){return admin;}

        String id;
        Boolean admin;

        public JwtResult(TOKEN_RESULT result, String id, Boolean admin) {
            this.result = result;
            this.id = id;
            this.admin = admin;
        }
    }

    public enum TOKEN_RESULT{
        OK, EXPIRED, NOT_TOKEN, UNAUTHORIZED;

        @Override
        public String toString(){
            return this.name();
        }
    };

    @Value("${jwt.expire}")
    private Long expire;

    private SecretKey key;

    public JwtUtil(@Value("${jwt.salt}") String salt){
        key = Keys.hmacShaKeyFor(salt.getBytes());
    }

    public String createAuthToken(String id, Boolean admin){
        return create(id, admin, "authToken", expire);
    }

    private String create(String id, Boolean admin, String Subject, long expire){
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(Subject).setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600 * expire));

        if(id != null)
            builder.claim("id", id);

        if(admin != null && admin == true)
            builder.claim("admin", true);


        builder.signWith(key);

        final String jwt = builder.compact();

        return jwt;
    }

    public String getId(final String jwt){
        return (String) Jwts.parserBuilder().build().parseClaimsJws(jwt).getBody().get("id");
    }

    public Map<String, Object> checkAndGetClaims(final String jwt){
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

        return claims.getBody();
    }

    public TOKEN_RESULT validate(String jwt, String id){
        try{
            Map<String, Object> claims = checkAndGetClaims(jwt);



            if(id.equals((String)claims.get("id"))){
                return TOKEN_RESULT.OK;
            }
            return TOKEN_RESULT.UNAUTHORIZED;
        }catch (ExpiredJwtException e){
            return TOKEN_RESULT.EXPIRED;
        }catch (MalformedJwtException e){
            return TOKEN_RESULT.NOT_TOKEN;
        }catch (Exception e){
            return TOKEN_RESULT.NOT_TOKEN;
        }
    }

    public JwtResult validate(String jwt){
        try{
            Map<String, Object> claims = checkAndGetClaims(jwt);

            return new JwtResult(TOKEN_RESULT.OK, (String) claims.get("id"), (Boolean) claims.get("admin"));
        }catch (ExpiredJwtException e){
            return new JwtResult(TOKEN_RESULT.EXPIRED, null, null);
        }catch (MalformedJwtException e){
            return new JwtResult(TOKEN_RESULT.NOT_TOKEN, null, null);
        }catch (Exception e){
            return new JwtResult(TOKEN_RESULT.NOT_TOKEN, null, null);
        }
    }
}