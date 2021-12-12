package aoc2021

fun main() {

    fun getLowPoints(heightMap: List<List<Int>>): List<Int> {
        val lowPoints: MutableList<Int> = ArrayList()
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
                    lowPoints.add(height)
                }
            }
        }

        return lowPoints.toList()
    }

    fun part1(input: List<String>): Int {
        val heightMap = input.map { it.toCharArray().map { digit -> digit.digitToInt() } }
        return getLowPoints(heightMap).sumOf { it + 1 }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}