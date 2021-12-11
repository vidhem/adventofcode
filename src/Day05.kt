import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val grid = MutableList(1000) { MutableList(1000) { 0 } }

        input.forEach { line ->
            val (p1, p2) = line.split(" -> ")
            val (x1, y1) = p1.split(",").map { it.toInt() }
            val (x2, y2) = p2.split(",").map { it.toInt() }

            if (x1 != x2 && y1 != y2) {
                return@forEach
            }

            if (x1 == x2) {
                for (index in min(y1, y2)..max(y1, y2)) {
                    grid[x1][index]++
                }
            }

            if (y1 == y2) {
                for (index in min(x1, x2)..max(x1, x2)) {
                    grid[index][y1]++
                }
            }
        }

        return grid.sumOf { it.filter { el -> el > 1 }.size }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}