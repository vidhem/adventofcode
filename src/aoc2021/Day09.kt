package aoc2021

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

fun main() {

    fun getLowPoints(heightMap: List<List<Int>>): List<Pair<Int,Int>> {
        val lowPoints: MutableList<Pair<Int,Int>> = ArrayList()
        val totalW = heightMap[0].size
        val totalH = heightMap.size

        heightMap.forEachIndexed { row, heights ->
            heights.forEachIndexed { column, height ->
                val comparisonPoints: MutableSet<Int> = HashSet()
                if (row > 0) {
                    comparisonPoints.add(heightMap[row-1][column])
                }
                if (row + 1 < totalH) {
                    comparisonPoints.add(heightMap[row+1][column])
                }
                if (column > 0) {
                    comparisonPoints.add(heightMap[row][column-1])
                }
                if (column + 1 < totalW) {
                    comparisonPoints.add(heightMap[row][column+1])
                }

                if (comparisonPoints.all { it > height }) {
                    lowPoints.add(Pair(row, column))
                }
            }
        }

        return lowPoints.toList()
    }

    fun part1(input: List<String>): Int {
        val heightMap = input.map { it.toCharArray().map { digit -> digit.digitToInt() } }
        return getLowPoints(heightMap).sumOf { heightMap[it.first][it.second] + 1 }
    }

    fun part2(input: List<String>): Int {
        val heightMap = input.map { it.toCharArray().map { digit -> digit.digitToInt() } }
        val totalW = heightMap[0].size
        val totalH = heightMap.size
        val lowPoints = getLowPoints(heightMap)

        val basinSizes = PriorityQueue<Int>(compareByDescending { it })

        for (lowPoint in lowPoints) {
            val queue: Queue<Pair<Int,Int>> = LinkedList()
            queue.add(lowPoint)

            val visited: MutableSet<Pair<Int,Int>> = HashSet()
            while (queue.isNotEmpty()) {
                val (row, column) = queue.remove()
                visited.add(Pair(row, column))

                if (row > 0 && heightMap[row-1][column] != 9 && !visited.contains(Pair(row-1, column))) {
                    queue.add(Pair(row-1, column))
                }
                if (row + 1 < totalH && heightMap[row+1][column] != 9 && !visited.contains(Pair(row+1, column))) {
                    queue.add(Pair(row+1, column))
                }
                if (column > 0 && heightMap[row][column-1] != 9 && !visited.contains(Pair(row, column-1))) {
                    queue.add(Pair(row, column-1))
                }
                if (column + 1 < totalW && heightMap[row][column+1] != 9 && !visited.contains(Pair(row, column+1))) {
                    queue.add(Pair(row, column+1))
                }
            }
            basinSizes.add(visited.size)
        }

        return basinSizes.remove() * basinSizes.remove() * basinSizes.remove()
    }

    val testInput = readInput("Day09_test")
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}