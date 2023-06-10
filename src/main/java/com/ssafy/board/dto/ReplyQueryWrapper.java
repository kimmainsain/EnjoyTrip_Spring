package com.ssafy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ReplyQueryWrapper extends Reply {
    String name;
    String userId;
    boolean admin;

    public ReplyQueryWrapper(Reply reply, String name, String userId){
        super(reply.toBuilder());
        this.name = name;
        this.userId = userId;
    }

    public ReplyQueryWrapper(Reply reply, String name, boolean admin){
        super(reply.toBuilder());
        this.name = name;
        this.admin = admin;
    }
}
