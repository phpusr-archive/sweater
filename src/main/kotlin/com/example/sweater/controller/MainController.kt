package com.example.sweater.controller

import com.example.sweater.domain.Message
import com.example.sweater.domain.MessageRepo
import com.example.sweater.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Controller
class MainController(val messageRepo: MessageRepo, @Value("\${upload.path}") val uploadPath: String) {

    @GetMapping("/")
    fun greeting(): String {
        return "greeting"
    }

    @GetMapping("/main")
    fun main(@RequestParam(defaultValue = "") filter: String, model: Model): String {
        model["messages"] = if (!filter.isBlank()) {
            messageRepo.findByTag(filter).reversed()
        } else {
            messageRepo.findAll().reversed()
        }
        model["filter"] = filter

        return "main"
    }

    @PostMapping("/add")
    fun add(
            @AuthenticationPrincipal user: User,
            @RequestParam text: String,
            @RequestParam tag: String,
            @RequestParam file: MultipartFile?
    ): String {
        val filename = if (file != null && !file.isEmpty) {
            val filename = "${UUID.randomUUID()}-${file.originalFilename}"
            file.transferTo(File("${uploadPath}/${filename}"))
            filename
        } else null

        val message = Message(0, text, tag, filename, user)
        messageRepo.save(message)

        return "redirect:/main"
    }

}