package com.example.sweater.controller

import com.example.sweater.domain.Role
import com.example.sweater.domain.User
import com.example.sweater.domain.UserRepo
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
class UserController(private val userRepo: UserRepo) {

    @GetMapping
    fun userList(model: Model): String {
        model["users"] = userRepo.findAll()

        return "userList"
    }

    @GetMapping("{user}")
    fun userEditForm(@PathVariable user: User, model: Model): String {
        model["user"] = user
        model["roles"] = Role.values()

        return "userEdit"
    }

    @PostMapping
    fun userSave(
            @RequestParam username: String,
            @RequestParam roles: Set<Role>,
            @RequestParam("userId") user: User
    ): String {
        user.username = username
        user.roles.clear()
        user.roles.addAll(roles)

        userRepo.save(user)

        return "redirect:/user"
    }

}