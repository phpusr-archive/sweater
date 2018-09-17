package com.example.sweater.domain.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CaptchaResponseDto(
        val success: Boolean,
        @JsonAlias("error-codes")
        val errorCodes: Set<String>?
)