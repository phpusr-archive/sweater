package com.example.sweater.domain.util

import com.example.sweater.domain.User

object MessageHelper {
    fun getAuthorName(author: User?): String {
        return author?.username ?: "<none>"
    }
}