package com.example.sweater.config

import com.example.sweater.util.RedirectInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig(@Value("\${upload.path}") val uploadPath: String) : WebMvcConfigurer {

    @Bean
    fun getRestTemplate() = RestTemplate()

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/login").setViewName("login")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://${uploadPath}/")
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(RedirectInterceptor())
    }
}