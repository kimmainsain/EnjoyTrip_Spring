package com.ssafy.board.dto;

import lombok.Data;

@Data
public class BoardQueryWrapper extends Board{
    private String name;
    private String keyword;

    // TODO 권한 관련해서 추가해야함.....

    private boolean admin;
    private String authorId;

    public BoardQueryWrapper(String name, Board board){
        super(board.toBuilder());
        this.setName(name);
    }

    public BoardQueryWrapper(String name, Board board, String authorId, boolean admin){
        super(board.toBuilder());
        this.setName(name);
        this.authorId = authorId;
        this.admin = admin;
    }

    public BoardQueryWrapper(String name, String keyword){
        this.name = name;
        this.keyword = keyword;
    }

    public BoardQueryWrapper(String name){
        this.name = name;
    }
}
