package com.ssafy.board.service;

import com.github.pagehelper.Page;
import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.BoardQueryWrapper;

import java.util.List;

public interface BoardService {
    boolean regist(String name, Board board);
    boolean delete(String name, int articleNo, String requestUserId, boolean isAdmin);
    Page<Board> list(String name);
    boolean edit(String name, Board board, String requestUserId, boolean isAdmin);
    boolean adminRequired(String name);
    Board select(String name, int articleNo);
    Page<Board> keywordSelect(String name, String keyword);
}
