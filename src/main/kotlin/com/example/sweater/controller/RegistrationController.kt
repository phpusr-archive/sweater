package com.example.sweater.controller

import com.example.sweater.domain.User
import com.example.sweater.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegistrationController(private val userService: UserService) {

    @GetMapping("/registration")
    fun registration(): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun addUser(user: User, model: Model): String {
        if (user.username.isBlank() || user.email.isBlank() || user.password.isBlank()) {
            model["message"] = "Blank username or email or password"
            return "registration"
        }

        if (!userService.addUser(user)) {
            model["message"] = "User exists!"
            return "registration"
        }

        return "redirect:/login"
    }

    @GetMapping("/activate/{code}")
    fun activate(model: Model, @PathVariable code: String): String {
        val isActivated = userService.activateUser(code)

        model["message"] = if (isActivated) "User successfully activated" else "Activation code is not found"

        return "login"
    }

}