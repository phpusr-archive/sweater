package com.example.sweater.service

import com.example.sweater.domain.Role
import com.example.sweater.domain.User
import com.example.sweater.domain.UserRepo
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepo: UserRepo

    @MockBean
    private lateinit var mailSender: MailService

    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun addUser() {
        val user = User(0, "", "1",false, "", "test@mail.ru")

        Mockito.doReturn("1")
                .`when`(passwordEncoder)
                .encode("1")

        val isUserCreated = userService.addUser(user)

        Assert.assertTrue(isUserCreated)
        Assert.assertNotNull(user.activationCode)
        Assert.assertTrue(CoreMatchers.`is`(user.roles).matches(mutableSetOf(Role.USER)))

        Mockito.verify(userRepo, Mockito.times(2)).save(user)
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                )
    }

    @Test
    fun addUserFailTest() {
        val user = User(0, "John", "1",false, "", "")

        Mockito.doReturn(User(0, "", "",false, "", ""))
                .`when`(userRepo)
                .findByUsername("John")

        val isUserCreated = userService.addUser(user)

        Assert.assertFalse(isUserCreated)

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User::class.java))
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                )
    }

    @Test
    fun activateTest() {
        val user = User(0, "", "", false, "bingo", "")
        Mockito.doReturn(user)
                .`when`(userRepo)
                .findByActivationCode("activate")

        val isUserActivated = userService.activateUser("activate")

        Assert.assertTrue(isUserActivated)
        Assert.assertNull(user.activationCode)
        Mockito.verify(userRepo, Mockito.times(1)).save(user)
    }

    @Test
    fun activateUserFailTest() {
        val isUserActivated = userService.activateUser("activate")

        Assert.assertFalse(isUserActivated)
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User::class.java))
    }

}