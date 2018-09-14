package com.example.sweater.controller

import com.example.sweater.domain.Role
import com.example.sweater.domain.User
import com.example.sweater.domain.UserRepo
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegistrationController(private val userRepo: UserRepo) {

    @GetMapping("/registration")
    fun registration(): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun addUser(user: User, model: Model): String {
        val dbUser = userRepo.findByUsername(user.username ?: "")
        if (dbUser != null) {
            model["message"] = "User exists! ${dbUser}"
            return "registration"
        }

        user.active = true
        user.authorities.add(Role.USER)
        userRepo.save(user)

        return "redirect:/login"
    }

}