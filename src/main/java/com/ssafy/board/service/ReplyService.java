package com.ssafy.board.service;

import com.ssafy.board.dto.Reply;
import com.ssafy.board.dto.ReplyQueryWrapper;
import com.ssafy.board.dto.ReplySelectQuery;

import java.util.List;

public interface ReplyService {
    List<Reply> selectReply(String name, int articleNo);
    boolean insertReply(String name, int articleNo, String userId, String content);
    boolean deleteReply(String name, int replyNo, String userId, boolean isAdmin);
}
