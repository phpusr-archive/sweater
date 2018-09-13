package com.example.sweater

import com.example.sweater.domain.Message
import com.example.sweater.domain.MessageRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingController {

    @Autowired
    lateinit var messageRepo: MessageRepo

    @GetMapping("/greeting")
    fun greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") name: String,
            model: Model
    ): String {
        model["name"] = name
        return "greeting"
    }

    @GetMapping
    fun main(model: Model): String {
        model["messages"] = messageRepo.findAll()

        return "main"
    }

    @PostMapping
    fun add(@RequestParam text: String, @RequestParam tag: String, model: Model): String {
        val message = Message(0, text, tag)
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