package com.ssafy.board.service;

import com.github.pagehelper.Page;
import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardQueryWrapper;
import com.ssafy.board.mapper.BoardMapper;
import com.ssafy.util.KMPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

    BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper){
        this.boardMapper = boardMapper;
    }

    @Override
    public boolean regist(String name, Board board) {
        BoardQueryWrapper query = new BoardQueryWrapper("board_" + name, board);
        boardMapper.regist(query);
        if(query.getArticleNo() == 0)
            return false;
        board.setArticleNo(query.getArticleNo());
        return true;
    }


    @Override
    public boolean delete(String name, int articleNo, String requestUserId, boolean isAdmin) {
        BoardQueryWrapper query = new BoardQueryWrapper("board_" + name, Board.builder().articleNo(articleNo).build(), requestUserId, isAdmin);
        return boardMapper.delete(query) == 1 ? true : false;
    }

    @Override
    public Page<Board> list(String name) {
        BoardQueryWrapper query = new BoardQueryWrapper("board_" + name + "_view");
        return boardMapper.list(query);
    }


    @Override
    public boolean edit(String name, Board board, String requestUserId, boolean isAdmin) {
        BoardQueryWrapper query = new BoardQueryWrapper("board_" + name, board, requestUserId, isAdmin);
        return boardMapper.edit(query) == 1 ? true : false;
    }

    @Override
    public boolean adminRequired(String name) {
        return boardMapper.adminRequired(name);
    }

    @Override
    public Board select(String name, int articleNo) {
        BoardQueryWrapper query = new BoardQueryWrapper("board_" + name + "_view", Board.builder().articleNo(articleNo).build());
        return boardMapper.select(query);
    }


    @Override
    public Page<Board> keywordSelect(String name, String keyword) {
        BoardQueryWrapper query = new BoardQueryWrapper("board_" + name + "_view", keyword);

        log.debug("board query : {}", query);

        Page<Board> page = boardMapper.keywordSelect(query);
        log.debug("list : {}", page);

        if(page == null)
            return null;

        int[] pi = KMPService.makeFailFunction(keyword);
        ArrayList<BoardWrapper> wrappingList = new ArrayList<>(page.size() + 1);

        for(Board data : page.getResult()){
            int occurence = KMPService.calcOccurence(data.getTitle(), keyword, pi);
            occurence += KMPService.calcOccurence(data.getContent(), keyword, pi);

            wrappingList.add(new BoardWrapper(occurence, data));
        }

        Collections.sort(wrappingList);

        Page<Board> rst = new Page<>(page.getPageNum(), page.getPageSize());


        for(BoardWrapper data :wrappingList){
            rst.add(data.data);
        }

        log.debug("result : {}", rst);

        return page;
    }

    private static class BoardWrapper implements Comparable<BoardWrapper>{
        int occurence;
        Board data;

        public BoardWrapper(int occurence, Board data) {
            this.occurence = occurence;
            this.data = data;
        }

        @Override
        public int compareTo(BoardWrapper o) {
            return Integer.compare(o.occurence, this.occurence);
        }
    }
}
