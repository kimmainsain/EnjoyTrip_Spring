package com.ssafy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class Reply {
    int replyNo;
    int userNo;
    int articleNo;
    String content;
    Timestamp time;
    String nickname;
}
