package com.ssafy.user.controller;

import com.ssafy.user.dto.User;
import com.ssafy.user.dto.UserSecret;
import com.ssafy.user.service.UserService;
import com.ssafy.jwt.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin("*")
@Api(tags="회원관리")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserRestController {
    UserService userService;
    JwtUtil jwtUtil;

    @Autowired
    public UserRestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @ApiOperation(value = "사용자 로그인", notes = "사용자의 id와 password(브라우제에서 단방향 해시된 패스워드를 입력 받는다.)를 통하여 서버 세션에 로그인을 처리한다. ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",  value = "id -> 유저 아이디, pw -> 단방향 해시된 패스워드", example = "{ \"id\" : \"ssafy\", \"pw\" : \"6aba834f044ee35f70a5faa0e2c22b67477b5ac6dcdcd83fc2ce906ba72a8e1d\" }")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody HashMap<String, String> param) {
        if (!param.containsKey("id") || !param.containsKey("pw"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        final String id = param.get("id");
        final String pw = param.get("pw");

        User user = userService.login(id, pw);

        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        HashMap<String, Object> res = new HashMap<>();
        res.put("nickname", user.getNickname());
        res.put("userNo", user.getNo());

        if (user.isAdmin())
            res.put("admin", true);
        res.put("jwt", jwtUtil.createAuthToken(id, user.isAdmin() ? true : null));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /* // JWT 쓰면 logout 필요 없음...
    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpSession session) {
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
     */

    @ApiOperation(value = "회원가입", notes = "아이디, 해시된 패스워드, 닉네임, 이메일을 받아서 회원 가입을 진행한다.\n { \"email\": \"test\", \"id\": \"test1234\", \"nickname\": \"레스트테스트\", \"pw\": \"247dfa6801d335380c31b584998ea9b48baab7c7fae706a12477598e29972dee\", \"hint\":\"광주초\"  }")
    @ApiImplicitParam(name = "user", value = "사용자 정보" , example = "{ \"email\": \"test\", \"id\": \"test1234\", \"nickname\": \"레스트테스트\", \"pw\": \"247dfa6801d335380c31b584998ea9b48baab7c7fae706a12477598e29972dee\", \"hint\":\"광주초\" }")
    @PostMapping
    public ResponseEntity<Boolean> singUp(@RequestBody UserSecret user) {
        if (userService.signUp(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "jwt 토큰과 hashing된 패스워드를 넘겨서 회원 탈퇴. \n{\n\"id\": \"test1234\",\n   \"pw\" : \"247dfa6801d335380c31b584998ea9b48baab7c7fae706a12477598e29972dee\"\n}")
    @ApiImplicitParam(name = "dat", example = "{\"id\": \"test1234\", \"pw\" : \"247dfa6801d335380c31b584998ea9b48baab7c7fae706a12477598e29972dee\"}")
    @DeleteMapping("/drop")
    public ResponseEntity<Boolean> dropOut(@RequestBody Map<String, String> dat,  @ApiParam(hidden = true) @RequestAttribute String jwtId) {
        String id = jwtId;

        if (!id.equals(dat.get("id")) || !dat.containsKey("pw"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        boolean rst = userService.dropOut(id, dat.get("pw"));

        if (rst) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ApiOperation(value = "사용자 패스워드 초기화", notes = "사용자의 아이디를 받아서 메일로 초기화된 패스워드를 전송한다.")
    @PostMapping("/reset")
    public ResponseEntity<String> reset(@ApiParam(value = "id, hint", example = "{ \"id\" : \"woojeong\", \"hint\" : \"싸피\"}") @RequestBody HashMap<String, String> body) {
        String id = body.get("id");
        String hint = body.get("hint");
        boolean rst = userService.reset(id, hint);

        log.debug("reset rst : {}", rst);

        if (!rst)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userService.select(id);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(user.getNickname(), HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "jwt 토큰과 수정할 사용자의 정보(아이디, 해시된 패스워드, 닉네임, 이메일)을 받아 수정한다. { \"email\": \"test\", \"id\": \"test1234\", \"nickname\": \"닉네임 변경\", \"pw\": \"247dfa6801d335380c31b584998ea9b48baab7c7fae706a12477598e29972dee\" }")
    @PutMapping
    public ResponseEntity<Boolean> edit(@RequestBody UserSecret secret, @ApiParam(hidden = true) @RequestAttribute String jwtId) {
        if(!jwtId.equals(secret.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean rst = userService.edit(secret);

        if (rst) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "아이디 활성 확인", notes = "조회하고자하는 id가 활성화되어있는지 반환합니다. (존재&활성화된 경우 true)")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkIfIdExists(@PathVariable String id) {
        boolean rst = userService.checkIfIdExists(id);

        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

    @ApiOperation(value = "아이디 사용 가능 확인", notes = "조회하고자하는 id 값이 사용가능한지 반환합니다. (사용가능한 경우 true)")
    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> checkIfIdAvailable(@PathVariable String id) {
        boolean rst = userService.checkIfIdAvailable(id);

        return new ResponseEntity<>(rst, HttpStatus.OK);
    }
    
    @GetMapping("/nickname")
    public ResponseEntity<Boolean> checkIfNicknameAvailable(@RequestParam String nickname){
        boolean rst = userService.checkIfNicknameAvailable(nickname);
        
        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

    @GetMapping("/basicInfo")
    public ResponseEntity<Map<String, String >> getBasicInfo(@ApiParam(hidden = true) @RequestAttribute String jwtId){
        return new ResponseEntity<>(userService.getBasicInfo(jwtId), HttpStatus.OK);
    }
}
