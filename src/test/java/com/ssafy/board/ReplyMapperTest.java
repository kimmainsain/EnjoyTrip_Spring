package com.ssafy.board;

import com.ssafy.board.dto.Reply;
import com.ssafy.board.dto.ReplyQueryWrapper;
import com.ssafy.board.dto.ReplySelectQuery;
import com.ssafy.board.mapper.ReplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class ReplyMapperTest {
    @Autowired
    ReplyMapper replyMapper;

    @Test
    public void insertTest() {
        ReplyQueryWrapper reply = ReplyQueryWrapper.builder().name("free").userId("ssafy").articleNo(4).content("댓글 테스트1").build();
        int rst = replyMapper.insertReply(reply);
        assertEquals(1, rst);
    }

    @Test
    public void selectTest() {
        List<Reply> list = replyMapper.selectReply(ReplySelectQuery.builder().name("free").articleNo(4).build());
        assertEquals(2, list.size());

        log.debug("list : {}", list);
    }

    @Test
    @Transactional
    public void deleteTest() {
        int rst = replyMapper.deleteReply(ReplyQueryWrapper.builder().replyNo(3).name("free").admin(false).userId("ssafy").build());
        assertEquals(1, rst);

        List<Reply> list = replyMapper.selectReply(ReplySelectQuery.builder().name("free").articleNo(4).build());
        assertEquals(1, list.size());

        log.debug("list : {}", list);

        rst = replyMapper.deleteReply(ReplyQueryWrapper.builder().replyNo(1).name("free").admin(false).userId("ssafy").build());
        assertEquals(0, rst);
    }
}
