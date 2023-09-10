package dev.blackshark.email.domain.service;
import java.io.File;

public interface IEmailService {

    void sendEmail(String[] toUsers, String subject, String body);

    void sendEmailWithFile(String[] toUsers, String subject, String body, File file);

    void sendEmail(String toUser, String subject, String body);

    void sendEmailWithFile(String toUser, String subject, String body, File file);
}
