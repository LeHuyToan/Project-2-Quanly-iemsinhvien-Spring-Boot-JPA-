package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	SpringTemplateEngine templateEngine;
	
	public void sendBirthdayEmail(String to, String name) {
		String subject = "HAPPY BIRTHDAY" + name;
		
		Context ctx = new Context();
		ctx.setVariable("name", name);
		String body = templateEngine.process("email/hpbd.html", ctx);
		
		sendEmail(to,subject,body);
		
	}
	
	public void sendEmail(String to , String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
		
		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			helper.setFrom("lht.word@gmail.com");
			
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void  testEmail() {
		String to = "lht942k2@gmail.com";
		String subject = "Tieu de email";
		String body = "<h1>Nội dung</h1>";
		
		sendEmail(to,subject,body);
		
	}
}
