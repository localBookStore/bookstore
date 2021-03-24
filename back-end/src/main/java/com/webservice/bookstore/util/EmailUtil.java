package com.webservice.bookstore.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Log4j2
public class EmailUtil {

    // 회원가입을 위한 인증번호 생성
    public static int randomint() {

        Random r = new Random();
        int dice = r.nextInt(4589362)+49311;
        return dice;

    }

    // 회원가입 요청 이메일 주소로 인증 메세지 송신
    public static void sendEmail(JavaMailSender javaMailSender,
                                 String email, String certificated) {

        String subject	= "[BookStore] 이메일 인증";
        String from		= "bookstore0324@gmail.com";
        String content	= "아래 링크를 통해 회원가입을 마무리할 수 있습니다!\n"
                            + "링크: http://localhost:8080/api/emailcheck/?email=%s&certificated=%s";
        String text	= String.format(content, email, certificated);

        try {
            // 메일 내용 넣을 객체와, 이를 도와주는 Helper 객체 생성
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true, "UTF-8");

            // 메일 내용을 채워줌
            messageHelper.setFrom(from);		// 보내는 사람 세팅
            messageHelper.setTo(email);			// 받는 사람 세팅
            messageHelper.setSubject(subject);	// 제목 세팅
            messageHelper.setText(text);	    // 내용 세팅

            javaMailSender.send(mail);	// 메일 전송

            log.info("메일 전송 완료 : To. " + email);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
