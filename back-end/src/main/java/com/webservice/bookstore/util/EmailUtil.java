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
        StringBuffer content = new StringBuffer();
        content.append("<!DOCTYPE html>");
        content.append("<html>");
        content.append("<head>");
        content.append("</head>");
        content.append("<body>");
        content.append(
                " <div" 																																																	+
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"		+
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"																															+
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">YG1110 BLOG</span><br />"																													+
                        "		<span style=\"color: #02b875\">메일인증</span> 안내입니다."																																				+
                        "	</h1>\n"																																																+
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+
                        "		<b style=\"color: #02b875\">인증 코드 : " + certificated +" </b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />"																													+
                        "		감사합니다."																																															+
                        "	</p>"																																																	+
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
                        " </div>"
        );
        content.append("</body>");
        content.append("</html>");
        String text	= String.format(content.toString(), email, certificated);

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
