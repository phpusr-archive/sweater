package com.example.sweater.controller

import com.example.sweater.domain.Role
import com.example.sweater.domain.User
import com.example.sweater.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    fun userList(model: Model): String {
        model["users"] = userService.findAll()

        return "userList"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    fun userEditForm(@PathVariable user: User, model: Model): String {
        model["user"] = user
        model["roles"] = Role.values()

        return "userEdit"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    fun userSave(
            @RequestParam username: String,
            @RequestParam(defaultValue = "") roles: Set<Role>,
            @RequestParam("userId") user: User
    ): String {
        if (username.isBlank()) {
            return "redirect:/user/${user.id}"
        }

        userService.saveUser(user, username, roles)

        return "redirect:/user"
    }

    @GetMapping("profile")
    fun profile(model: Model, @AuthenticationPrincipal user: User): String {
        model["username"] = user.username
        model["email"] = user.email

        return "profile"
    }

    @PostMapping("profile")
    fun profile(
            @AuthenticationPrincipal user: User,
            @RequestParam password: String,
            @RequestParam email: String
    ): String {
        userService.updateProfile(user, password, email)

        return "redirect:/user/profile"
    }

    @GetMapping("subscribe/{user}")
    fun subscribe(
            @AuthenticationPrincipal currentUser: User,
            @PathVariable user: User
    ): String {
        userService.subscribe(currentUser, user)

        return "redirect:/user-messages/${user.id}"
    }

    @GetMapping("unsubscribe/{user}")
    fun unsubscribe(
            @AuthenticationPrincipal currentUser: User,
            @PathVariable user: User
    ): String {
        userService.unsubscribe(currentUser, user)

        return "redirect:/user-messages/${user.id}"
    }

    @GetMapping("{type}/{user}/list")
    fun userList(
            model: Model,
            @PathVariable type: String,
            @PathVariable user: User
    ): String {
        model["userChannel"] = user
        model["type"] = type
        model["users"] = if (type == "subscribtions") user.subscribtions else user.subscribers

        return "subscribtions"
    }

}