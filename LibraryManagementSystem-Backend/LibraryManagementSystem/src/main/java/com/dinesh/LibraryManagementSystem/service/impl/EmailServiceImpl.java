package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String to, String subject, String body) {

		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			javaMailSender.send(mimeMessage);

		} catch (MessagingException | MailException e) {

			throw new MailSendException("Failed to send mail", e);
		}
	}
}