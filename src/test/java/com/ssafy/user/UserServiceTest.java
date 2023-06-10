package com.ssafy.user;

import com.ssafy.user.dto.User;
import com.ssafy.user.dto.UserSecret;
import com.ssafy.user.service.UserService;
import com.ssafy.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class UserServiceTest {

    UserService userService;
    SqlSession sqlSession;

    @Autowired
    public UserServiceTest(UserService userService, SqlSession sqlSession){
        this.userService = userService;
        this.sqlSession = sqlSession;
    }

    @Test
    public void hashingTest() throws Exception {
        String salt = UserServiceImpl.generateSalt();
        String pw = UserServiceImpl.generateSalt();

        String hash = UserServiceImpl.hashing(salt, pw);

        assertNotEquals(salt, hash);
        assertNotEquals(pw, hash);
    }


    @Test
    public void loginTest() { // User login(String id, String pw) throws Exception;
        String id = "ssafy";
        String pw = "1234";
        String hash = UserServiceImpl.hashing(id, pw);
        User user = userService.login("ssafy", hash);
        assertEquals("관리자", user.getNickname());
        assertEquals(true, user.isAdmin());

        pw = "ssafy";
        hash = UserServiceImpl.hashing(id, pw);
        user = userService.login("ssafy", hash);
        assertNull(user);

        id = "test";
        pw = "test";
        hash = UserServiceImpl.hashing(id, pw);
        user = userService.login(id, hash);
        assertNull(user);


        id = "woojeong";
        pw = "8nePVNPcmj0=";
        hash = UserServiceImpl.hashing(id, pw);
        user = userService.login(id, hash);
        assertNotNull(user);
        assertEquals("woojeong", user.getNickname());
    }

    @Test
    @Transactional
    public void signUpTest(){ // boolean signUp(UserSecret user)
        UserSecret user = UserSecret.builder().id("woojeong").nickname("woojeong")
                .email("wj6349@gmail.com").build();
        String salt = UserServiceImpl.generateSalt();
        String pw = "1234";
        user.setSalt(salt);
        user.setPw(UserServiceImpl.hashing(user.getId(), pw));

        boolean rst = userService.signUp(user);

        assertEquals(true, rst);
    }

    @Test
    @Transactional
    public void dropOutTest(){ // boolean dropOut(String id);
        boolean rst = userService.dropOut("woojeong", "");
        assertEquals(true, rst);
        rst = userService.dropOut("test", "");
        assertEquals(false, rst);
    }

    @Test
    @Transactional
    public void resetTest(){ // boolean reset(String id);
        boolean rst = userService.reset("woojeong", "싸피");
        assertEquals(true, rst);

        rst = userService.reset("test", "싸피");
        assertEquals(false, rst);
    }

    @Test
    @Transactional
    public void idChckTest(){
        boolean rst = userService.checkIfIdAvailable("ssafy");
        assertEquals(false, rst);
        sqlSession.clearCache();
        rst = userService.checkIfIdExists("ssafy");
        assertEquals(true, rst);
        sqlSession.clearCache();
        rst = userService.checkIfIdAvailable("ssafy2");
        assertEquals(true, rst);
        sqlSession.clearCache();
        rst = userService.checkIfIdExists("ssafy2");
        assertEquals(false, rst);
    }
}
