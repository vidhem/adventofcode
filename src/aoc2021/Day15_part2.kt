package aoc2021

import java.util.*

fun main() {

    data class Node(val point: Pair<Int, Int>, val distance: Int = Int.MAX_VALUE) : Comparable<Node> {
        override fun compareTo(other: Node): Int {
            return this.distance - other.distance
        }
    }

    fun part2(input: List<String>): Int {
        val grid: MutableList<MutableList<Int>> = ArrayList()

        val parsedInput = input.map { it.toCharArray().map { c -> c.digitToInt() } }
        var tile = parsedInput.toTypedArray().copyOf().toList()

        repeat(5) {
            tile.forEach { grid.add(it.toMutableList()) }
            tile = tile.map { it.map { d ->
                val new = d + 1
                if (new > 9) 1 else new
            }}
        }

        repeat(4) { index ->
            grid.forEach { row ->
                row.addAll(row.subList(index * input.size, (index + 1) * input.size).map {
                    if (it + 1 > 9) 1 else it + 1
                })
            }
        }

        val visited: MutableSet<Pair<Int, Int>> = HashSet()
        val distances = MutableList(grid.size) { MutableList(grid[0].size) { Int.MAX_VALUE } }

        val traversal = PriorityQueue<Node>()
        traversal.add(Node(Pair(0, 0), 0))

        while (visited.size < grid.size * grid[0].size) {
            val node = traversal.remove()
            if (visited.contains(node.point)) {
                continue
            }

            val (x, y) = node.point

            distances[y][x] = node.distance
            visited.add(node.point)

            val edges = HashSet<Pair<Int, Int>>()
            if (x > 0) {
                edges.add(Pair(x-1, y))
            }
            if (y > 0) {
                edges.add(Pair(x, y-1))
            }
            if (x < grid[0].size - 1) {
                edges.add(Pair(x+1, y))
            }
            if (y < grid.size - 1) {
                edges.add(Pair(x, y+1))
            }

            edges.forEach {
                if (distances[it.second][it.first] > grid[it.second][it.first] + node.distance) {
                    distances[it.second][it.first] = grid[it.second][it.first] + node.distance
                    traversal.add(Node(Pair(it.first, it.second), distances[it.second][it.first]))
                }
            }
        }

        return distances.last().last()
    }

    val testInput = readInput("Day15_test")
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part2(input))
}