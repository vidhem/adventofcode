package aoc2021

fun main() {
    val segments = listOf(
        "abcefg",
        "cf",
        "acdeg",
        "acdfg",
        "bcdf",
        "abdfg",
        "abdefg",
        "acf",
        "abcdefg",
        "abcdfg"
    ).map { it.toCharArray().toSet() }

    val digits = segments.map { it.size }

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
        return input.fold(0) { acc, line ->
            val signal = line.split(" | ")
            val characters = signal[0].split(" ")
                .map { it.toCharArray().toMutableSet() }

            val one = characters.find { it.size == digits[1] } ?: HashSet()
            val four = characters.find { it.size == digits[4] } ?: HashSet()
            val seven = characters.find { it.size == digits[7] } ?: HashSet()
            val eight = characters.find { it.size == digits[8] } ?: HashSet()

            val a = seven - one
            val eg = eight - four - a
            val bd = four - one

            val fiveSegments = characters.filter { it.size == 5 } // 2, 3, 5
            val dg = fiveSegments.drop(1).fold(fiveSegments[0]) { acc, it -> acc.intersect(it).toMutableSet() } - a
            val g = eg.intersect(dg)
            val d = dg - g
            val e = eg - g
            val b = bd - d

            val sixSegments = characters.filter { it.size == 6 }
            val abgf = sixSegments.drop(1).fold(sixSegments[0]) { acc, it -> acc.intersect(it).toMutableSet() }
            val f = abgf - a - b - g
            val c = one - f

            val patterns = listOf(
                setOf(a, b, c, e, f, g),
                setOf(c, f),
                setOf(a, c, d, e, g),
                setOf(a, c, d, f, g),
                setOf(b, c, d, f),
                setOf(a, b, d, f, g),
                setOf(a, b, d, e, f, g),
                setOf(a, c, f),
                setOf(a, b, c, d, e, f, g),
                setOf(a, b, c, d, f, g),
            ).map { it.flatten().toSet() }

            val display = signal[1].split(" ")
                .map { it.toCharArray().toSet() }
                .joinToString("") { patterns.indexOfFirst { segments -> (segments == it) }.toString() }
                .toInt()

            acc + display
        }
    }

    val testInput = readInput("Day08_test")
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}