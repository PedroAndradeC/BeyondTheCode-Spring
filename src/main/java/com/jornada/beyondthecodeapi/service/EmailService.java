package com.jornada.beyondthecodeapi.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final freemarker.template.Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String from;
    public void enviarEmailSimples (String emailDestino, String assunto, String texto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(emailDestino);
        mailMessage.setSubject(assunto);
        mailMessage.setText(texto);

        this.emailSender.send(mailMessage);
    }
    public void enviarEmailComAnexos(String emailDestino, String assunto, String texto) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);

        helper.setFrom(from);
        helper.setTo(emailDestino);
        helper.setSubject(assunto);
        helper.setText(texto);

        // Forma 1
        File file1 = new File("C:\\Users\\USER\\Desktop\\Projeto Spring\\BeyondTheCode-Spring\\src\\main\\resources\\pp.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);

        // Forma 2
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file2 = new File(classLoader.getResource("filipe.png").getFile());
//        FileSystemResource file = new FileSystemResource(file2);
//        helper.addAttachment(file2.getName(), file);

        emailSender.send(message);
    }
    public void enviarEmailComTemplate(String emailDestino, String assunto, String texto) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(emailDestino);
            mimeMessageHelper.setSubject(assunto);
            String email = gerarConteudoComTemplate("Teste", from);
            mimeMessageHelper.setText(email, true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    public String gerarConteudoComTemplate(String nome, String email) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", nome);
        dados.put("email", email);

        Template template = fmConfiguration.getTemplate("template-email.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
