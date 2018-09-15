package com.example.sweater.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig(
        @Value("\${spring.mail.host}") private val host: String,
        @Value("\${spring.mail.username}") private val username: String,
        @Value("\${spring.mail.password}") private val password: String,
        @Value("\${spring.mail.port}") private val port: Int,
        @Value("\${spring.mail.protocol}") private val protocol: String,
        @Value("\${mail.debug}") private val debug: String
) {

    @Bean("sweaterMailSender")
    fun getMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.username = username
        mailSender.password = password
        mailSender.port = port
        mailSender.protocol = protocol

        val props = mailSender.javaMailProperties
        props["mail.debug"] = debug

        return mailSender
    }

}