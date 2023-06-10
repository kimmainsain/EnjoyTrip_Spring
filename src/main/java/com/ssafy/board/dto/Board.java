package com.ssafy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Board {
    private int articleNo;
    private int authorNo;
    private String title;
    private String content;
    private Timestamp time;
    private boolean enabled;
    private String id;
    private String nickName;

    private boolean isEnabled(){
        return enabled;
    }
}
