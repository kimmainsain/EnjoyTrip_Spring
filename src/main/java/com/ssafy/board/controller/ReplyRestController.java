package com.ssafy.board.controller;

import com.ssafy.board.dto.Reply;
import com.ssafy.board.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Api(value = "댓글 REST 서비스")
@RestController
@RequestMapping("/api/board")
public class ReplyRestController {

    ReplyService replyService;

    @Autowired
    public ReplyRestController(ReplyService replyService){
        this.replyService = replyService;
    }

    @GetMapping("/{name}/reply/{articleNo}")
    public ResponseEntity<List<Reply>> selectReply(@ApiParam(value = "게시판 이름") @PathVariable String name, @ApiParam(value = "게시글 번호") @PathVariable int articleNo){
        List<Reply> rst = replyService.selectReply(name, articleNo);

        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

    @PostMapping("/{name}/reply/{articleNo}")
    public ResponseEntity<Boolean> insertReply(@ApiParam(value = "게시판 이름") @PathVariable String name, @ApiParam(value = "게시글 번호") @PathVariable int articleNo, @ApiParam("댓글 내용") @RequestBody Reply reply, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        boolean rst = replyService.insertReply(name, articleNo, jwtId, reply.getContent());

        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

    @DeleteMapping("/{name}/{articleNo}/{replyNo}")
    public ResponseEntity<Boolean> deleteReply(@ApiParam(value = "게시판 이름") @PathVariable String name, @ApiParam(value = "댓글 번호") @PathVariable int replyNo, @ApiParam(hidden = true) @RequestAttribute String jwtId, @ApiParam(hidden = true) @RequestAttribute Boolean admin){
        boolean rst = replyService.deleteReply(name, replyNo, jwtId, admin);

        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

}
