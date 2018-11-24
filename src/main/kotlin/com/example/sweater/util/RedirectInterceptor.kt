package com.example.sweater.util

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RedirectInterceptor : HandlerInterceptorAdapter() {
    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        if (modelAndView != null) {
            val args = if (request.queryString != null) "${request.queryString}?" else ""
            val url = "${request.requestURI}${args}"
            response.setHeader("Turbolinks-Location", url)
        }
    }
}