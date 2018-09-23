package com.example.sweater.test

import org.junit.Assert
import org.junit.Test

class SimpleTest {

    @Test
    fun test() {
        val x = 2
        val y = 23

        Assert.assertEquals(46, x * y)
        Assert.assertEquals(25, x + y)
    }

    @Test(expected = ArithmeticException::class)
    fun error() {
        1 / 0
    }

}