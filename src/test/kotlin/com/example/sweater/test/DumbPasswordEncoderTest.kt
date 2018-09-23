package com.example.sweater.test

import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class DumbPasswordEncoderTest {

    @Test
    fun encode() {
        val encoder = DumbPasswordEncoder()

        Assert.assertEquals("secret: 'mypwd'", encoder.encode("mypwd"))
        Assert.assertThat(encoder.encode("mypwd"), Matchers.containsString("mypwd"))
    }
}