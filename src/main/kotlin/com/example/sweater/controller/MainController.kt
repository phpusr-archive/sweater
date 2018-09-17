package com.example.sweater.controller

import com.example.sweater.domain.Message
import com.example.sweater.domain.MessageRepo
import com.example.sweater.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import javax.validation.Valid

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

    @PostMapping("/main")
    fun add(
            @AuthenticationPrincipal user: User,
            @RequestParam file: MultipartFile?,
            @Valid message: Message,
            bindingResult: BindingResult,
            model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult))
            model["messages"] = messageRepo.findAll().reversed()

            return "main"
        }

        saveFile(file, message)
        message.author = user
        messageRepo.save(message)

        return "redirect:/main"
    }

    private fun saveFile(file: MultipartFile?, message: Message) {
        val filename = if (file != null && !file.isEmpty) {
            val filename = "${UUID.randomUUID()}-${file.originalFilename}"
            file.transferTo(File("${uploadPath}/${filename}"))
            filename
        } else null

        message.filename = filename
    }

    @GetMapping("/user-messages/{user}")
    fun userMessages(
            @AuthenticationPrincipal currentUser: User,
            @PathVariable user: User,
            model: Model,
            @RequestParam(required = false) message: Message?
    ): String {
        model["messages"] = user.messages
        if (message != null) {
            model["message"] = message
        }
        model["isCurrentUser"] = user.id == currentUser.id

        return "userMessages"
    }

    @PostMapping("/user-messages/{user}")
    fun updateMessage(
            @AuthenticationPrincipal currentUser: User,
            @PathVariable user: Long,
            @RequestParam("id") message: Message,
            @RequestParam file: MultipartFile?,
            @RequestParam text: String,
            @RequestParam tag: String
    ): String {
        if (message.author?.id == currentUser.id && text.isNotBlank() && tag.isNotBlank()) {
            val newMessage = message.copy(text = text, tag = tag)
            if (file != null && !file.isEmpty) {
                saveFile(file, newMessage)
            }
            messageRepo.save(newMessage)
        }

        return "redirect:/user-messages/${user}"
    }

}