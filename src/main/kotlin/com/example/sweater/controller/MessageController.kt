package com.example.sweater.controller

import com.example.sweater.domain.Message
import com.example.sweater.domain.MessageRepo
import com.example.sweater.domain.User
import com.example.sweater.service.MessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.util.UriComponentsBuilder
import java.io.File
import java.util.*
import javax.validation.Valid

@Controller
class MessageController(
        val messageRepo: MessageRepo,
        @Value("\${upload.path}")
        val uploadPath: String,
        val messageService: MessageService
) {

    @GetMapping("/")
    fun greeting(): String {
        return "greeting"
    }

    @GetMapping("/main")
    fun main(
            @RequestParam(defaultValue = "") filter: String,
            model: Model,
            @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
            @AuthenticationPrincipal currentUser: User
    ): String {
        model["page"] = messageService.messageList(filter, pageable, currentUser)
        model["filter"] = filter
        model["url"] = "/main"

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

    @GetMapping("/user-messages/{author}")
    fun userMessages(
            @AuthenticationPrincipal currentUser: User,
            @PathVariable author: User,
            model: Model,
            @RequestParam(required = false) message: Message?,
            @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): String {
        model["userChannel"] = author
        model["subscribtionsCount"] = author.subscribtions.size
        model["subscribersCount"] = author.subscribers.size
        model["isSubscriber"] = author.subscribers.contains(currentUser)
        model["page"] = messageService.messageListForUser(pageable, currentUser, author)
        if (message != null) {
            model["message"] = message
        }
        model["isCurrentUser"] = author.id == currentUser.id
        model["url"] = "/user-messages/${author.id}"

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

    @GetMapping("/messages/{message}/like")
    fun like(
            @AuthenticationPrincipal currentUser: User,
            @PathVariable message: Message,
            redirectAttributes: RedirectAttributes,
            @RequestHeader(required = false) referer: String
    ): String {
        val likes = message.likes

        if (likes.contains(currentUser)) {
            likes.remove(currentUser)
        } else {
            likes.add(currentUser)
        }

        messageRepo.save(message)

        val components = UriComponentsBuilder.fromHttpUrl(referer).build()
        components.queryParams.entries.forEach { pair ->
            redirectAttributes.addAttribute(pair.key, pair.value)
        }

        return "redirect:${components.path}"
    }

}