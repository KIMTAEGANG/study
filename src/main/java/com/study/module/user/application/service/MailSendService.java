package com.study.module.user.application.service;

import com.study.module.user.application.port.input.MailSendUseCase;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class MailSendService implements MailSendUseCase {

    @Value("${spring.mail.username}")
    private String sender;

    private JavaMailSenderImpl javaMailSender;

    @Override
    public String sendAuthCode(String email) {
        String authCode = makeAuthCode();
        MimeMessage message = javaMailSender.createMimeMessage();
        String from = sender;
        String to = email;
        String title = "회원 가입 인증 이메일";
        String context = "인증 번호는 " + authCode + "입니다." + "<br/><br/>"
                + "인증번호를 입력해 확인란에 기입하여 주세요";

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(title);
            messageHelper.setText(context, true);
        } catch (Exception e) {
            throw new MailSendException("mail 전송 에러");
        }

        return authCode;
    }

    private String makeAuthCode() {
        SecureRandom secureRandom = new SecureRandom();
        String code = "";
        for(int i=0; i<6; i++) {
            code += secureRandom.nextInt(10);
        }
        return code;
    }
}
