package com.ssafy.board.mapper;

import com.ssafy.board.dto.Reply;
import com.ssafy.board.dto.ReplyQueryWrapper;
import com.ssafy.board.dto.ReplySelectQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<Reply> selectReply(ReplySelectQuery query);
    int insertReply(ReplyQueryWrapper reply);
    int deleteReply(ReplyQueryWrapper query);
}
