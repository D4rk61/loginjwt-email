package dev.blackshark.email.domain.service;

import dev.blackshark.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String[] toUsers, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        MailConfig mailConfig = new MailConfig();

        mailMessage.setFrom(mailConfig.getEmail());

        mailMessage.setTo(toUsers);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithFile(String[] toUsers, String subject, String body, File file) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MailConfig mailConfig = new MailConfig();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailConfig.getEmail());

            mimeMessageHelper.setTo(toUsers);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.addAttachment(file.getName(), file);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(String toUser, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        MailConfig mailConfig = new MailConfig();

        mailMessage.setFrom(mailConfig.getEmail());

        mailMessage.setTo(toUser);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithFile(String toUser, String subject, String body, File file) {

        MimeMessage  mimeMessage = mailSender.createMimeMessage();
        MailConfig mailConfig = new MailConfig();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailConfig.getEmail());

            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.addAttachment(file.getName(), file);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
