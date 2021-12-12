package aoc2021

fun main() {
    fun part1(input: List<String>): Int {
        var timers = input[0].split(",")
            .map { it.toInt() }
            .toMutableList()

        repeat(80) {
            val newGens = timers.count { it == 0 }
            timers = timers.map { if (it < 7) (it -1 + 7)%7 else (it - 1) }.toMutableList()
            repeat(newGens) {
                timers.add(8)
            }
        }

        return timers.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)

    val input = readInput("Day06")
    println(part1(input))
}