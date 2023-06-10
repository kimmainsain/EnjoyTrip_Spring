package com.ssafy.user.service;

import com.ssafy.user.dto.User;
import com.ssafy.user.dto.UserSecret;

import java.util.Map;

public interface UserService {
	User login(String id, String pw);
	boolean signUp(UserSecret user);
	boolean dropOut(String id, String pw);
	boolean reset(String id, String hint);
	boolean edit(UserSecret user);
	User select(String id);

	boolean checkIfIdAvailable(String id);
	boolean checkIfIdExists(String id);

	boolean checkIfNicknameAvailable(String nickname);
	Map<String, String> getBasicInfo(String id);
}
