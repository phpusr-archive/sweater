package com.example.sweater.domain

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    USER;

    override fun getAuthority() = name
}