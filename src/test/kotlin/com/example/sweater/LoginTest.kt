package com.example.sweater

import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class LoginTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun test() {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("Hello, guest!")))
                .andExpect(content().string(containsString("Log in")))
    }

    @Test
    fun accessDeniedTest() {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("http://localhost/login"))
    }

    @Test
    @Sql(value = ["/create-user-before.sql", "/messages-list-before.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun correctLoginTest() {
        mockMvc.perform(formLogin().user("admin").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/"))
    }

    @Test
    fun badCredentials() {
        mockMvc.perform(post("/login").param("user", "Alfred"))
                .andDo(print())
                .andExpect(status().isForbidden)
    }
}