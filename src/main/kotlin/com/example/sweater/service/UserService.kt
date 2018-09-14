package com.example.sweater.service

import com.example.sweater.domain.UserRepo
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepo: UserRepo) : UserDetailsService {

    override fun loadUserByUsername(username: String) = userRepo.findByUsername(username)

}