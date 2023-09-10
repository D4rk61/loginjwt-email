package dev.blackshark.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Getter     @Setter
@Configuration
public class MailConfig {

    private String email = "reynosojose2005@gmail.com";     //  <-- Correo electronico con el que enviaremos el email
    private String passwd = "btdcnczvzkbgpgcv";                   //  <-- Contraseña del correo electronico

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // propiedades
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(passwd);

        Properties properties = mailSender.getJavaMailProperties();

        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug","true");

        return mailSender;
    }
}

