package com.example.sweater.controller

import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = ["/create-user-before.sql", "/messages-list-before.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
// TODO repeatable annotations not work
// @Sql(value = ["/messages-list-after.sql", "/create-user-after.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MainControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun mainPageTest() {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/nav/div/a").string("admin"))
    }

    @Test
    fun messageListTest() {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[3]/div").nodeCount(4))
    }

    @Test
    fun filterMessageTest() {
        mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[3]/div").nodeCount(2))
                .andExpect(xpath("/html/body/div/div[3]/div[@data-id=1]").exists())
                .andExpect(xpath("/html/body/div/div[3]/div[@data-id=3]").exists())
    }

    @Test
    fun addMessageToList() {
        val index = 10
        val multipart = multipart("/main")
                .file("file", "123".toByteArray())
                .param("text", "fifth")
                .param("tag", "new one")
                .with(csrf())
        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/main"))

        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[3]/div").nodeCount(5))
                .andExpect(xpath("/html/body/div/div[3]/div[@data-id=${index}]").exists())
                .andExpect(xpath("/html/body/div/div[3]/div[@data-id=${index}]/div/h5").string("#${index} new one"))
                .andExpect(xpath("/html/body/div/div[3]/div[@data-id=${index}]/div/p").string(containsString("fifth")))
    }

}