package com.example.sweater.controller

import com.example.sweater.domain.User
import com.example.sweater.domain.dto.CaptchaResponseDto
import com.example.sweater.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate
import javax.validation.Valid

@Controller
class RegistrationController(
        private val userService: UserService,
        private val restTemplate: RestTemplate,
        @Value("\${recaptcha.secret}") private val recaptchaSecret: String
) {

    @GetMapping("/registration")
    fun registration(): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun addUser(
            @RequestParam("password2") passwordConfirm: String,
            @RequestParam("g-recaptcha-response") captchaResponse: String,
            @Valid user: User,
            bindingResult: BindingResult,
            model: Model
    ): String {
        val url = "https://www.google.com/recaptcha/api/siteverify?secret=${recaptchaSecret}&response=${captchaResponse}"
        val response = restTemplate.postForObject(url, null,
                CaptchaResponseDto::class.java)

        if (response == null || !response.success) {
            val captchaError = response?.errorCodes?.toString() ?: "Captcha error"
            bindingResult.addError(FieldError("user", "captcha", null, true,
                    null, null, captchaError))
        }

        if (passwordConfirm.isBlank()) {
            bindingResult.addError(FieldError("user", "password2", null, true,
                    null, null, "Password confirmation can't be empty"))
        }

        if (user.password != passwordConfirm) {
            bindingResult.addError(FieldError("user", "password2", null, true,
                    null, null, "Password are different!"))
        }

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult))
            return "registration"
        }

        if (!userService.addUser(user)) {
            model["usernameError"] = "User exists!"
            return "registration"
        }

        return "redirect:/login"
    }

    @GetMapping("/activate/{code}")
    fun activate(model: Model, @PathVariable code: String): String {
        val isActivated = userService.activateUser(code)

        model["message"] = if (isActivated) "User successfully activated" else "Activation code is not found"
        model["messageType"] = if (isActivated) "success" else "danger"

        return "login"
    }

}