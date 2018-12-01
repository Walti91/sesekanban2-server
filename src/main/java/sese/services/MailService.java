package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async("AsyncExecutor")
    public void sendMail(String from, String to, String subject, String text) {
        sendMailWithAttachment(from, to, subject, text, null, null, null);
    }

    @Async("AsyncExecutor")
    public void sendMailWithAttachment(String from, String to, String subject, String text, String attachmentName, byte[] attachment, String attachmentType) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {

            MimeMessageHelper messageHelper;

            if (Objects.nonNull(attachment)) {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.addAttachment(attachmentName, new ByteArrayResource(attachment), attachmentType);
            }
            else {
                messageHelper = new MimeMessageHelper(mimeMessage, false);
            }

            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
        };
        mailSender.send(messagePreparator);
    }
}
