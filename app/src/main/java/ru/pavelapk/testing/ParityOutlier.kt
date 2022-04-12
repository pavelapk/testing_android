package ru.pavelapk.testing

import kotlin.math.abs

class ParityOutlier {

    fun find(integers: Array<Int>): Int? {
        if (integers.size < 3) throw IndexOutOfBoundsException("The length of the array must be at least 3")
        val parity = listOf(integers[0], integers[1], integers[2])
            .map { abs(it) % 2 }.sorted()[1]
        return integers.find { abs(it) % 2 != parity }
    }
}