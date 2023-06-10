package com.ssafy.mail;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

	/*
	public static void main(String[] args) {
		System.out.println("start main!");
		System.out.println(instance.getInstance().sendReset("ssafy9woojeong@gmail.com", "123"));
		System.out.println("end main!");
	}
	 */
	

	@Override
	public boolean sendReset(String email, String pw) {
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		final String sender_email = "ssafy9woojeong@gmail.com";
		final String sender_pw = "ybqjxieuuxxbznla";
		final String encoding = "utf-8";
		
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.quitwait", "false");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender_email, sender_pw);
            }
        });
        
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(sender_email));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("[SSAFY Enjoy Trip] 패스워드 초기화 안내", "utf-8");
            
            String content = "초기화된 비밀번호는 " + pw + "입니다.\n해당 비밀번호를 이용하여 로그인 후 패스워드 변경하시기 바랍니다.";
            message.setText(content, "utf-8");
 
            Transport.send(message);    // send message
            
            return true;
 
 
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
 
		return false;
	}
}
