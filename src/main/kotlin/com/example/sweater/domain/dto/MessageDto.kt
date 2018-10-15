package com.example.sweater.domain.dto

import com.example.sweater.domain.Message
import com.example.sweater.domain.User
import com.example.sweater.domain.util.MessageHelper

class MessageDto(
        val id: Long?,
        val text: String,
        val tag: String,
        val author: User?,
        val filename: String?,
        val likes: Long,
        val meLiked: Boolean
) {
    constructor(message: Message, likes: Long, meLiked: Boolean) : this(message.id, message.text, message.tag,
            message.author, message.filename, likes, meLiked)

    fun getAuthorName() = MessageHelper.getAuthorName(author)
}