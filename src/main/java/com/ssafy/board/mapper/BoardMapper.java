package com.ssafy.board.mapper;

import com.github.pagehelper.Page;
import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
Service에서 board name을 알맞게 정의해줘야함
e.g) board_free -> insert
     board_free_view -> select
     notice -> adminReq

 */

@Mapper
public interface BoardMapper {
    void regist(BoardQueryWrapper query);
    int delete(BoardQueryWrapper query);
    Page<Board> list(BoardQueryWrapper query);
    int edit(BoardQueryWrapper query);
    boolean adminRequired(String name);
    Board select(BoardQueryWrapper query);
    Page<Board> keywordSelect(BoardQueryWrapper query);
}
