package com.ssafy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {
    private int no;
    private String id;
    private String nickname;
    private String email;
    private boolean admin;

    public User(UserSecret user){
        this.no = user.getNo();
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.admin = user.isAdmin();
    }
}

