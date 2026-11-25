package ru.ustinov.mailsender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 08.01.2024
 */
@Slf4j
@Service
public class MailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public MailResult sendEmail(String email, String subject, String body) {
        log.info("Send mail to '" + email + "' subject '" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        String state = MailResult.OK;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom("ivanustinov1985@yandex.ru", "VotingApp");
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            state = e.getMessage();
        }
        log.info("Sent with state: " + state);
        return new MailResult(email, state);
    }

}
