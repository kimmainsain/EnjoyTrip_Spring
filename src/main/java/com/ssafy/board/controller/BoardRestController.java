package com.ssafy.board.controller;

import com.github.pagehelper.Page;
import com.ssafy.board.dto.Board;
import com.ssafy.board.service.BoardService;
import com.ssafy.paging.PagingResult;
import com.ssafy.user.dto.User;
import com.ssafy.jwt.JwtUtil;
import com.ssafy.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/board")
@CrossOrigin("*")
@Api(tags = {"게시판"})
public class BoardRestController {
    BoardService boardService;
    UserService userService;
    JwtUtil jwtUtil;

    @Autowired
    public BoardRestController(BoardService boardService, UserService userService, JwtUtil jwtUtil){
        this.boardService = boardService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @ApiOperation(value = "게시글 등록", notes = "jwt 토큰을 이용하여, name 게시판에 게시글(제목, 내용)을 등록합니다. {  \"title\": \"REST 테스트\",  \"content\": \"REST 테스트입니다~~~~\"}")
    @PostMapping("/{name}") // boolean regist(String name, Board board);
    public ResponseEntity<String> regist(@ApiParam(value = "게시판 이름") @PathVariable String name, @ApiParam(value = "게시글", example = "{  \"title\": \"REST 테스트\",  \"content\": \"REST 테스트입니다~~~~\"}") @RequestBody Board board, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        boolean adminReq = boardService.adminRequired(name);
        String id = jwtId;
        User user = userService.select(id);

        if(user == null || (adminReq && !user.isAdmin())){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        board.setAuthorNo(user.getNo());

        boolean rst = boardService.regist(name, board);

        if(rst){
            log.debug("no : {}", board.getArticleNo());
            return new ResponseEntity<>(Integer.toString(board.getArticleNo()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "게시글 삭제", notes = "jwt 토큰과 게시판 이름, 게시글 번호를 받아 게시글을 삭제합니다. {  \"articleNo\": 29 }")
    @DeleteMapping("/{name}") // boolean delete(String name, int articleNo);
    public ResponseEntity<Boolean> delete(@PathVariable String name, @RequestParam int articleNo, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        String id = jwtId;
        User user = userService.select(id);
        //

        if(user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean rst = boardService.delete(name, articleNo, id, user.isAdmin());

        if(rst){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{name}/paging") // Page<Board> list(String name);
    public ResponseEntity<PagingResult<Board>> list(@PathVariable String name){
        Page<Board> page = boardService.list(name);
        log.debug(name + " result : {}", page);

        return new ResponseEntity<>(new PagingResult<>(page), HttpStatus.OK);
    }


    @ApiOperation(value = "게시글 수정", notes = "jwt 토큰과 게시글 정보(글번호, 제목, 내용) 그리고 아이디를 받아 게시글을 수정합니다.{ \"articleNo\": 29, \"title\": \"REST 수정\",  \"content\": \"REST 수정 테스트입니다~~~~\", \"id\": \"ssafy\"}")
    @PutMapping("/{name}") // boolean edit(String name, Board board);
    public ResponseEntity<Boolean> edit(@PathVariable String name, @RequestBody Board board, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        String id = jwtId;
        User user = userService.select(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean rst = boardService.edit(name, board, id, user.isAdmin());

        if(rst){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "글 선택", notes = "게시판에서 특정한 글을 선택하여 반환합니다.")
    @GetMapping("/{name}/{articleNo}") // Board select(String name, int articleNo);
    public ResponseEntity<Board> select(@ApiParam(value = "게시판") @PathVariable String name, @ApiParam(value = "글번호") @PathVariable int articleNo){
        Board board = boardService.select(name, articleNo);

        if(board == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(board, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "게시글 검색", notes = "키워드를 이용하여 게시글을 검색하여, 키워드가 많이 나온 순으로 정렬하여 반환합니다.")
    @GetMapping("/{name}/search/paging") // List<Board> keywordSelect(String name, String keyword);
    public ResponseEntity<PagingResult<Board>> keywordSearch(@PathVariable String name, @RequestParam(required = false) String keyword) {
        log.debug("name : {}, keyword : {}", name,  keyword);
        Page<Board> list = boardService.keywordSelect(name, keyword);

        return new ResponseEntity<>(new PagingResult<>(list), HttpStatus.OK);
    }
}
