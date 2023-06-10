package com.ssafy.util;

import com.ssafy.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class JwtTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void jwtGenTest(){
        String jwt = jwtUtil.createAuthToken("ssafy", null);

        log.debug("JWT : {}", jwt);
    }

    @Test
    public void jwtClaim(){
        String jwt = jwtUtil.createAuthToken("ssafy", null);
        Map<String, Object> claims = jwtUtil.checkAndGetClaims(jwt);

        log.debug("claim : {}", claims);
        assertEquals(claims.get("id"), "ssafy");
    }

    @Test
    public void expireTest() throws InterruptedException {
        String jwt = jwtUtil.createAuthToken("ssafy", null);
        Thread.sleep(1000);

        assertThrows(ExpiredJwtException.class, () ->jwtUtil.checkAndGetClaims(jwt));
    }

    @Test
    public void validationExpireTest() throws Exception{
        // need to change expire time to 0

        String jwt = jwtUtil.createAuthToken("ssafy", null);
        Thread.sleep(1000);

        assertEquals(JwtUtil.TOKEN_RESULT.EXPIRED, jwtUtil.validate(jwt, "ssafy"));
    }

    @Test
    public void validationUnauthorizedTest(){
        String jwt = jwtUtil.createAuthToken("ssafy", null);

        assertEquals(JwtUtil.TOKEN_RESULT.UNAUTHORIZED, jwtUtil.validate(jwt, "ssafy2"));
    }

    @Test
    public void validdationNotToken(){
        assertEquals(JwtUtil.TOKEN_RESULT.NOT_TOKEN, jwtUtil.validate("1234", "ssafy"));
    }

    @Test
    public void validationOKTest(){
        String jwt = jwtUtil.createAuthToken("ssafy", null);

        assertEquals(JwtUtil.TOKEN_RESULT.OK, jwtUtil.validate(jwt, "ssafy"));
    }

    @Test
    public void abc(){
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
