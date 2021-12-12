package aoc2021

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        val minPos = positions.minOrNull() ?: 0
        val maxPos = positions.maxOrNull() ?: 0

        return (minPos..maxPos).minOf { positions.sumOf { pos -> abs(pos - it) } }
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        val minPos = positions.minOrNull() ?: 0
        val maxPos = positions.maxOrNull() ?: 0

        return (minPos..maxPos).minOf { positions.sumOf { pos -> (abs(pos - it) * (abs(pos - it) + 1))/2 } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}