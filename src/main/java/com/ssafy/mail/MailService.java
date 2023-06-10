package com.ssafy.mail;

public interface MailService {
	boolean sendReset(String email, String pw);
}
