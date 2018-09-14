package com.example.sweater.controller

import com.example.sweater.domain.Message
import com.example.sweater.domain.MessageRepo
import com.example.sweater.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MainController {

    @Autowired
    private lateinit var messageRepo: MessageRepo

    @GetMapping("/")
    fun greeting(model: Model): String {
        return "greeting"
    }

    @GetMapping("/main")
    fun main(model: Model): String {
        model["messages"] = messageRepo.findAll()

        return "main"
    }

    @PostMapping("/main")
    fun add(
            @AuthenticationPrincipal user: User,
            @RequestParam text: String,
            @RequestParam tag: String, model: Model
    ): String {
        val message = Message(0, text, tag, user)
        messageRepo.save(message)
        model["messages"] = messageRepo.findAll()

        return "main"
    }

    @PostMapping("/filter")
    fun filter(@RequestParam filter: String, model: Model): String {
        model["messages"] = if (filter.isBlank()) messageRepo.findAll() else messageRepo.findByTag(filter)

        return "main"
    }

}