package com.ssafy.board;

import com.github.pagehelper.Page;
import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardQueryWrapper;
import com.ssafy.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class BoardMapperTest {
    BoardMapper boardMapper;

    @Autowired
    public BoardMapperTest(BoardMapper boardMapper){
        this.boardMapper = boardMapper;
    }

    @Test
    public void beanTest(){
        assertNotNull(boardMapper);
    }

    @Test
    @Transactional
    public void insertTest(){
        Board board = Board.builder().title("테스트입니다").content("자유게시판 테스트!").authorNo(1).build();
        BoardQueryWrapper query = new BoardQueryWrapper("board_free", board);
        boardMapper.regist(query);

        assertNotEquals(0, query.getArticleNo());
    }

    @Test
    public void listTest(){
        BoardQueryWrapper query = new BoardQueryWrapper("board_notice");
        Page<Board> list = boardMapper.list(query);

        // 테스트 할 때 글 수에 맞춰서 태스트해줘야함...
        assertEquals(2, list.size());
    }

    @Test
    @Transactional
    public void deleteTest(){
        BoardQueryWrapper query = new BoardQueryWrapper("board_notice", Board.builder().articleNo(2).build());
        query.setAdmin(false);
        query.setAuthorId("test");
        int rst = boardMapper.delete(query);

        assertEquals(0, rst);

        query.setAdmin(true);

        rst = boardMapper.delete(query);

        assertEquals(1, rst);

        query = new BoardQueryWrapper("board_notice", Board.builder().articleNo(3).build(), "ssafy", false);

        rst = boardMapper.delete(query);

        assertEquals(1, rst);
    }

    @Test
    public void selectTest(){ // board select 하는 건 서비스에서 _view를 붙여줄 예정...
        BoardQueryWrapper query = new BoardQueryWrapper("board_notice_view", Board.builder().articleNo(2).build());
        Board article = boardMapper.select(query);

        assertEquals(article.getNickName(), "관리자");

        log.debug("article : {}", article);
    }

    @Test
    @Transactional
    public void editTest(){
        BoardQueryWrapper query = new BoardQueryWrapper("board_notice", Board.builder().articleNo(2).title("수정~").content("글 수정 했어요!!").build(), "test", false);
        int rst = boardMapper.edit(query);
        assertEquals(0, rst);

        query.setAdmin(true);
        rst = boardMapper.edit(query);
        assertEquals(1, rst);

        query.setArticleNo(3);
        query.setAdmin(false);
        query.setAuthorId("ssafy");
        rst = boardMapper.edit(query);

        assertEquals(1, rst);
    }

    @Test
    public void adminReqTest(){
        boolean rst = boardMapper.adminRequired("notice");

        assertEquals(true, rst);

        rst = boardMapper.adminRequired("free");

        assertEquals(false, rst);
    }

    @Test
    public void keywordSearchTest(){
        BoardQueryWrapper query = new BoardQueryWrapper("board_notice", "test");
        List<Board> list = boardMapper.keywordSelect(query);

        log.debug("{}", list);
    }
}
