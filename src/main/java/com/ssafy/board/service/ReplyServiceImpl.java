package com.ssafy.board.service;

import com.ssafy.board.dto.Reply;
import com.ssafy.board.dto.ReplyQueryWrapper;
import com.ssafy.board.dto.ReplySelectQuery;
import com.ssafy.board.mapper.ReplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService {

    ReplyMapper replyMapper;

    @Autowired
    public ReplyServiceImpl(ReplyMapper replyMapper){
        this.replyMapper = replyMapper;
    }

    @Override
    public List<Reply> selectReply(String name, int articleNo) {
        return replyMapper.selectReply(ReplySelectQuery.builder().name(name).articleNo(articleNo).build());
    }

    @Override
    public boolean insertReply(String name, int articleNo, String userId, String content) {
        return replyMapper.insertReply(ReplyQueryWrapper.builder().name(name).articleNo(articleNo).userId(userId).content(content).build()) == 1 ? true : false;
    }

    @Override
    public boolean deleteReply(String name, int replyNo, String userId, boolean isAdmin) {
        return replyMapper.deleteReply(ReplyQueryWrapper.builder().name(name).replyNo(replyNo).userId(userId).admin(isAdmin).build()) == 0 ? false : true;
    }
}
