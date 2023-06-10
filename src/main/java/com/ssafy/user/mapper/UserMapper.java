package com.ssafy.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user.dto.User;
import com.ssafy.user.dto.UserSecret;

import java.util.Map;

@Mapper
public interface UserMapper {
	int signUp(UserSecret user);
	User select(String id);
	UserSecret selectSecret(String id);
	int dropout(String id);
	int reset(UserSecret user);
	String getEmail(String id);
	int edit(UserSecret user);

	boolean checkIfIdAvailable(String id);
	boolean checkIfIdEnabled(String id);

	boolean checkDuplicatedNickname(String nickname);

	Map<String, String> getBasicInfo(String id);
}
