package com.example.sweater

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingController {

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
        model["some"] = "Let's get fun!"
        return "main";
    }

}