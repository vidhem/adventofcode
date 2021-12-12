package aoc2021

fun main() {
    val digits = listOf(6, 2, 5, 5, 4, 5, 6, 3, 7, 6)

    fun part1(input: List<String>): Int {
        val outputValues = input.map { it.split(" | ")[1].split(" ") }
        return outputValues.sumOf { it.count { output ->
            output.length == digits[1]
                    || output.length == digits[4]
                    || output.length == digits[7]
                    || output.length == digits[8]
        }}
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}