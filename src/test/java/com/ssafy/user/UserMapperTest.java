package com.ssafy.user;

import com.ssafy.user.dto.User;
import com.ssafy.user.dto.UserSecret;
import com.ssafy.user.mapper.UserMapper;
import com.ssafy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Slf4j
public class UserMapperTest {

    UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Test
    @Transactional
    public void signUpTest(){ //(#{id}, #{pw}, #{nickname}, #{email}, #{salt})
        UserSecret user = UserSecret.builder().id("test123").pw("test").salt("test").email("test@test.com").nickname("tester11").build();

        int rst = userMapper.signUp(user);
        assertEquals(1, rst);
        log.debug("signUp : {}", user);
        assertEquals("test123", user.getId());
        //assertEquals(3, user.getNo());
    }

    @Test
    @Transactional
    public void dropoutTest(){
        int rst = userMapper.dropout("test");

        assertEquals(1, rst);
    }

    @Test
    public void selectTest(){
        User user = userMapper.select("test");
        assertNull(user);

        user = userMapper.select("ssafy");
        assertEquals("관리자", user.getNickname());
        assertEquals(1, user.getNo());


        UserSecret userSecret = userMapper.selectSecret("ssafy");
        assertEquals("4hXouqZA150=", userSecret.getSalt());
    }

    @Test
    @Transactional
    public void resetTest(){
        UserSecret userSecret = UserSecret.builder().id("ssafy").pw("1234").salt("444").build();
        int rst = userMapper.reset(userSecret);
        assertEquals(1, rst);

        UserSecret userSecret2 = userMapper.selectSecret("ssafy");
        assertEquals("1234", userSecret2.getPw());
        assertEquals("444", userSecret2.getSalt());
    }

    @Test
    public void getMailTest(){
        String email = userMapper.getEmail("ssafy");
        assertEquals("ssafy@ssafy.com", email);
    }

    @Test
    @Transactional
    public void editTest(){
        UserSecret userSecret = UserSecret.builder().id("ssafy").pw("1234").salt("hihi").nickname("ssaficial").email("test").build();
        int rst = userMapper.edit(userSecret);
        assertEquals(1, rst);

        User user = userMapper.select("ssafy");
        assertEquals("ssaficial", user.getNickname());

        userSecret.setId("test");
        rst = userMapper.edit(userSecret);
        assertEquals(0, rst);
    }

    @Test
    @Transactional
    public void idDupTest(){
        boolean rst = userMapper.checkIfIdAvailable("ssafy");
        assertEquals(false, rst);

        rst = userMapper.checkIfIdAvailable("ssafy1");
        assertEquals(true, rst);

        rst = userMapper.checkIfIdEnabled("ssafy");
        assertEquals(true, rst);
        rst = userMapper.checkIfIdEnabled("test1234");
        assertEquals(false, rst);
    }

    @Test
    public void  nicknameDupTest(){
        boolean rst = userMapper.checkDuplicatedNickname("관리자");
        assertEquals(true, rst);

        rst = userMapper.checkDuplicatedNickname("ssafy");
        assertEquals(false, rst);
    }
}
