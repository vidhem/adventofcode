package aoc2024

import kotlin.math.abs

fun main() {
    fun part1(pairs: List<String>): Int {
        val firstList = ArrayList<Int>()
        val secondList = ArrayList<Int>()

        pairs.forEach { 
            val nums = it.split(" ")
            firstList.add(nums.first().toInt())
            secondList.add(nums.last().toInt())
        }

        firstList.sort()
        secondList.sort()

        return firstList.foldIndexed(0) { index, acc, element ->
            acc + abs(secondList[index] - element)
        }
    }

    fun part2(pairs: List<String>): Int {
        val firstList = ArrayList<Int>()
        val freqs = HashMap<Int, Int>()

        pairs.forEach {
            val nums = it.split(" ")
            firstList.add(nums.first().toInt())
            freqs[nums.last().toInt()] = freqs.getOrPut(nums.last().toInt()) { 0 } + 1
        }

        return firstList.fold(0) { acc, element ->
            acc + element * freqs.getOrDefault(element, 0)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
