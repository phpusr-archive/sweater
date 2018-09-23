package com.example.sweater.test

import org.springframework.security.crypto.password.PasswordEncoder

class DumbPasswordEncoder : PasswordEncoder {

    override fun encode(rawPassword: CharSequence?): String {
        return "secret: '${rawPassword}'"
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return encode(rawPassword) == encodedPassword
    }

}