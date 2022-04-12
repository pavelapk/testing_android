package ru.pavelapk.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object {
        @JvmStatic
        fun positiveTests() = listOf(
            Arguments.of(arrayOf(2, 1, 3, 5, 7), 2),
            Arguments.of(arrayOf(1, 2, 4, 6, 8, 10), 1),
            Arguments.of(arrayOf(-2, -1, -3, -5, -7), -2),
            Arguments.of(arrayOf(1, -1, 2, 3, -3), 2),
            Arguments.of(arrayOf(Int.MAX_VALUE, 0, 1), 0),
            Arguments.of(arrayOf(Int.MIN_VALUE, 0, 1), 1),
        )
    }

    @ParameterizedTest
    @MethodSource("positiveTests")
    internal fun positiveTest(input: Array<Int>, output: Int) {
        val parityOutlier = ParityOutlier()
        val result = parityOutlier.find(input)
        assertEquals(output, result)
    }

    @Test
    internal fun testMaxAndMinInteger() {
        val parityOutlier = ParityOutlier()
        val result1 = parityOutlier.find(arrayOf(Int.MAX_VALUE, 0, Int.MIN_VALUE))
        val result2 = parityOutlier.find(arrayOf(Int.MAX_VALUE, 1, Int.MIN_VALUE))
        assertAll(
            { assertEquals(Int.MAX_VALUE, result1) },
            { assertEquals(Int.MIN_VALUE, result2) }
        )
    }

    @Test
    internal fun testLongArray() {
        val input = Array(1000) { 100 }
        input[500] = 123
        val parityOutlier = ParityOutlier()
        val result = parityOutlier.find(input)
        assertEquals(123, result)
    }

    @Test
    internal fun testAllEven() {
        val parityOutlier = ParityOutlier()
        val result = parityOutlier.find(arrayOf(0, 0, 0))
        assertNull(result)
    }

    @Test
    internal fun testAllOdd() {
        val parityOutlier = ParityOutlier()
        val result = parityOutlier.find(arrayOf(-1, 1, -1))
        assertNull(result)
    }

    @Test
    internal fun testShortArray() {
        val parityOutlier = ParityOutlier()
        assertThrowsExactly(IndexOutOfBoundsException::class.java) {
            parityOutlier.find(arrayOf(0, 1))
        }
    }

    @Test
    internal fun testEmptyArray() {
        val parityOutlier = ParityOutlier()
        assertThrowsExactly(IndexOutOfBoundsException::class.java) {
            parityOutlier.find(arrayOf())
        }
    }

}