package com.ssafy.board;

import com.github.pagehelper.Page;
import com.ssafy.board.dto.Board;
import com.ssafy.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
public class BoardServiceTest {
    BoardService boardService;

    @Autowired
    public BoardServiceTest(BoardService boardService){
        this.boardService = boardService;
    }

    @Test
    @Transactional
    public void registTest(){ // int regist(String name, Board board);
        Board board = Board.builder().authorNo(1).title("서비스 테스트!").content("서비스 테스트 입니다!").build();
        boolean rst = boardService.regist("free", board);

        assertEquals(true, rst);
        assertNotEquals(0, board.getArticleNo());
    }

    @Test
    @Transactional
    public void deleteTest(){ // int delete(String name, int articleNo);
        boolean rst = boardService.delete("notice", 3, "ssafy", true);
        assertEquals(true, rst);

        rst = boardService.delete("notice", 5, "test", false);
        assertEquals(false, rst);

        rst = boardService.delete("notice", 5,"test", true);
        assertEquals(true, rst);

        rst = boardService.delete("notice", 6, "ssafy", false);
        assertEquals(true,rst);
    }

    @Test
    public void listTest(){ // Page<Board> list(String name);
        Page<Board> page = boardService.list("notice");
        assertEquals(5, page.size());
        log.debug("list : {}", page);
    }

    @Test
    @Transactional
    public void editTest(){ // int edit(String name, Board board);
        Board board = boardService.select("notice", 5);
        board.setContent("수정했어!");
        boolean rst = boardService.edit("notice", board, "ssafy", true);
        assertEquals(true, rst);
    }

    @Test
    public void adminReqTest(){ // boolean adminRequired(String name);
        boolean rst = boardService.adminRequired("notice");
        assertEquals(true, rst);
        rst = boardService.adminRequired("free");
        assertEquals(false, rst);
    }

    @Test
    public void selectTest(){ // Board select(String name, int articleNo);
        Board board = boardService.select("notice", 4);
        assertEquals("testtest", board.getTitle());
        assertEquals("456", board.getContent());
        log.debug("select : {}", board);
    }

    @Test void keywordSearchTest() throws Exception { // List<Board> keywordSelect(String name, String keyword);
        List<Board> list = boardService.keywordSelect("notice", "test");
        assertEquals(3, list.size());
    }

}
