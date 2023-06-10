package com.ssafy.user.service;

import com.ssafy.mail.MailService;
import com.ssafy.mail.MailServiceImpl;
import com.ssafy.user.dto.User;
import com.ssafy.user.dto.UserSecret;
import com.ssafy.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    UserMapper userMapper;
    MailService mailService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, MailService mailService){
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @Override
    public User login(String id, String pw) {
        UserSecret savedUser = userMapper.selectSecret(id);
        if(savedUser == null)
            return null;

        String hash = hashing(savedUser.getSalt(), pw);
        log.debug("salt : {}", savedUser.getSalt());
        log.debug("hash : {}", hash);
        log.debug("pw : {}", pw);
        log.debug("pw2 : {}", savedUser.getPw());

        if(!hash.equals(savedUser.getPw()))
            return null;

        savedUser.setSalt(null);
        savedUser.setPw(null);

        return new User(savedUser);
    }

    @Override
    public boolean signUp(UserSecret user) {
        String salt = generateSalt();
        String hash = hashing(salt, user.getPw());

        user.setPw(hash);
        user.setSalt(salt);
        int rst = userMapper.signUp(user);

        user.setPw(null);
        user.setSalt(null);

        if(rst == 1)
            return true;
        else return false;
    }

    @Override
    public boolean dropOut(String id, String pw) {
        log.debug("id : {}, pw  : {}", id, pw);
        User saved = login(id, pw);
        log.debug("drop : {}", saved);
        if(saved == null)
            return false;

        int rst = userMapper.dropout(id);

        if(rst == 1)
            return true;
        else return false;
    }


    @Override
    public boolean reset(String id, String hint) {
        UserSecret user = userMapper.selectSecret(id);
        log.debug("input hint : {}", hint);
        log.debug("saved hint : {}", user.getHint());
        if(user == null || !user.getHint().equals(hint))
            return false;
        String pw = generateSalt();
        log.debug("new pw : {}", pw);
        String salt = generateSalt();

        StringBuilder saved = new StringBuilder(pw);
        String hash = hashing(id, pw);

        user.setSalt(salt);
        user.setPw(hashing(salt, hash));

        int rst = userMapper.reset(user);
        log.debug("transaction : {}", rst);

        if(rst == 1) {
            mailService.sendReset(user.getEmail(), saved.toString());

            return true;
        }
        else return false;
    }

    @Override
    public boolean edit(UserSecret user) {
        UserSecret saved = userMapper.selectSecret(user.getId());

        if(saved == null)
            return false;

        String salt = saved.getSalt();
        user.setPw(hashing(salt, user.getPw()));

        int rst = userMapper.edit(user);
        if(rst == 1)
            return true;
        else return false;
    }

    @Override
    public User select(String id) {
        return userMapper.select(id);
    }

    @Override
    public boolean checkIfIdAvailable(String id) {
        boolean rst = userMapper.checkIfIdAvailable(id);
        return rst;
    }

    @Override
    public boolean checkIfIdExists(String id) {
        boolean rst = userMapper.checkIfIdEnabled(id);
        return rst;
    }

    @Override
    public boolean checkIfNicknameAvailable(String nickname) {
        return !userMapper.checkDuplicatedNickname(nickname);
    }

    public static String hashing(String salt, String key){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String raw = (salt + key);
            md.update(raw.getBytes());

            byte[] digest = md.digest();

            return decode(digest);
        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
    }

    public static String decode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String generateSalt() {
        String salt = null;

        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[8];
            random.nextBytes(bytes);

            salt = new String(Base64.getEncoder().encode(bytes));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getCause());
        }

        return salt;
    }

    @Override
    public Map<String, String> getBasicInfo(String id){
        return userMapper.getBasicInfo(id);
    }
}
