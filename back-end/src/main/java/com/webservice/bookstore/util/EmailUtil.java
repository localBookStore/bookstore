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

    // 비밀번호 찾기를 위한 숫자 및 알파벳으로 이루어진 랜덤 문자열 생성
    public static String randomString() {

        int leftLimit   = 48;   // numeral '0'
        int rightLimit  = 122;  // letter 'z'
        int targetStringLength = 12;
        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

    }

    // 회원가입 요청 이메일 주소로 인증 메세지 송신
    public static void sendEmail(JavaMailSender javaMailSender,
                                 String email, String certificated) {

        String from		= "bookstore0324@gmail.com";
        String subject  = null;
        String content  = null;
        if(certificated.length() > 7) {
            subject = "[BookStore] 임시 비밀번호 발급";
            content = "임시 비밀번호를 발급해드립니다.\n"
                           + "임시 비밀번호: " + certificated + "\n"
                           + "로그인하신 후에는 꼭 비밀번호를 변경해주세요!";
        } else {
            subject = "[BookStore] 이메일 인증";
            content = "이메일 인증 확인 메일입니다.\n"
                           + "인증코드: " + certificated;
        }

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
