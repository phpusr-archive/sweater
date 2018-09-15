package com.example.sweater.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
        @Qualifier("sweaterMailSender") private val mailSernder: JavaMailSender,
        @Value("\${spring.mail.username}") private val username: String ) {

    fun send(emailTo: String, subject: String, message: String) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setFrom(username)
        mailMessage.setTo(emailTo)
        mailMessage.setSubject(subject)
        mailMessage.setText(message)

        mailSernder.send(mailMessage)
    }

}