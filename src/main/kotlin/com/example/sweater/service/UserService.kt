package com.example.sweater.service

import com.example.sweater.domain.Role
import com.example.sweater.domain.User
import com.example.sweater.domain.UserRepo
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepo: UserRepo, private val mailSender: MailService) : UserDetailsService {

    override fun loadUserByUsername(username: String) = userRepo.findByUsername(username)

    fun addUser(user: User): Boolean {
        val dbUser = userRepo.findByUsername(user.username)

        if (dbUser != null) {
            return false
        }

        user.authorities.add(Role.USER)
        sendActivationCode(user)
        userRepo.save(user)

        return true
    }

    fun sendActivationCode(user: User) {
        user.activationCode = UUID.randomUUID().toString()
        user.active = false
        userRepo.save(user)

        val message = "Hello, ${user.username}!\n" +
                "Welcome to sweater. Please, visit next link: http://localhost:8080/activate/${user.activationCode}"
        mailSender.send(user.email, "Activation code", message)
    }

    fun activateUser(code: String): Boolean {
        val user = userRepo.findByActivationCode(code)

        if (user == null) {
            return false
        }

        user.activationCode = null
        user.active = true
        userRepo.save(user)

        return true
    }

    fun findAll() = userRepo.findAll()

    fun saveUser(user: User, username: String, roles: Set<Role>) {
        user.username = username
        user.roles.clear()
        user.roles.addAll(roles)

        userRepo.save(user)
    }

    fun updateProfile(user: User, password: String, email: String) {
        val isEmailChanged = email.isNotBlank() && email != user.email
        if (isEmailChanged) {
            user.email = email
        }

        if (password.isNotBlank()) {
            user.password = password
        }

        userRepo.save(user)

        if (isEmailChanged) {
            sendActivationCode(user)
        }
    }

}