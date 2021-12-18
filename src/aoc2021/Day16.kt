package aoc2021

fun main() {
    fun part1(input: List<String>): Int {
        val binary = input[0].toCharArray()
            .map {
                it.digitToInt(16)
                    .toString(2)
                    .padStart(4, '0')
            }
            .joinToString("") { it }

        return 0
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 16)
}