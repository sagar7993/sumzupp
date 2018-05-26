package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class MailService {
    private static final String TAG = "MailService : ";

    private JavaMailSender javaMailSender;

    private SimpleMailMessage simpleMailMessage;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @SuppressWarnings("resource")
    public void buildEmail(String[] to, String from, String replyTo, String subject, String body, String fileUrl, String fileName) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Mail.xml");

        MailService mailService = (MailService) applicationContext.getBean("mail");
        mailService.sendEmail(to, from, replyTo, subject, body, fileUrl, fileName);
    }

    private void sendEmail(String[] to, String from, String replyTo, String subject, String body, String fileUrl, String fileName) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setReplyTo(replyTo);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            if(!StringUtils.isEmpty(fileUrl) && !StringUtils.isEmpty(fileName)) {
                FileSystemResource file = new FileSystemResource(fileUrl);
                helper.addAttachment(fileName, file);
            }

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }

    public void sendTextMail(String toMail, String subject, String content){
        buildEmail(new String[]{toMail}, Constants.FROM_EMAIL, Constants.REPLY_TO_EMAIL, subject, content, null, null);
    }

    public void sendFile(String toMail, String filePath){
        FileSystemResource file = new FileSystemResource(filePath);

        buildEmail(new String[]{toMail}, Constants.FROM_EMAIL, Constants.REPLY_TO_EMAIL, "Requested File", "PFA", file.getPath(), file.getFilename());
    }

    public void sendFileToMultiplePeople(String[] toMail, String filePath){
        FileSystemResource file = new FileSystemResource(filePath);

        buildEmail(toMail, Constants.FROM_EMAIL, Constants.REPLY_TO_EMAIL, "Requested File", "PFA", file.getPath(), file.getFilename());
    }

}
