package com.example.sweater.controller

import org.springframework.validation.BindingResult

object ControllerUtils {

    fun getErrors(bindingResult: BindingResult) =
            bindingResult.fieldErrors.map { "${it.field}Error" to it.defaultMessage }.toMap()

}