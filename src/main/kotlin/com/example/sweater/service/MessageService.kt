package com.example.sweater.service

import com.example.sweater.domain.MessageRepo
import com.example.sweater.domain.User
import com.example.sweater.domain.dto.MessageDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MessageService(val messageRepo: MessageRepo) {

    fun messageList(filter: String, pageable: Pageable, user: User): Page<MessageDto> {
        if (!filter.isBlank()) {
            return messageRepo.findByTag(filter, pageable, user)
        }

        return messageRepo.findAll(pageable, user)
    }

    fun messageListForUser(pageable: Pageable, user: User, author: User): Page<MessageDto> {
        return messageRepo.findByUser(pageable, author, user)
    }

}