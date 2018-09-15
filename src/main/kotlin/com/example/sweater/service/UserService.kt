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
        user.activationCode = UUID.randomUUID().toString()
        userRepo.save(user)

        val message = "Hello, ${user.username}!\n" +
                "Welcome to sweater. Please, visit next link: http://localhost:8080/activate/${user.activationCode}"
        mailSender.send(user.email, "Activation code", message)

        return true
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

}