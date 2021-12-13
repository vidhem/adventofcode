package aoc2021

import java.util.*

fun main() {

    fun part2(input: List<String>): Int {
        val nodes: MutableMap<String, Node> = HashMap()
        input.forEach {
            val (v1, v2) = it.split("-")
            nodes[v1] = nodes.getOrDefault(v1, Node(v1))
            nodes[v2] = nodes.getOrDefault(v2, Node(v2))

            nodes[v1]!!.edges.add(nodes[v2]!!)
            nodes[v2]!!.edges.add(nodes[v1]!!)
        }

        val paths: MutableList<List<Node>> = ArrayList()
        val queue: Queue<MutableList<Node>> = LinkedList()
        queue.add(mutableListOf(nodes["start"]!!))

        while (queue.isNotEmpty()) {
            val front = queue.remove()
            if (front.last().value == "end") {
                paths.add(front)
                continue
            }

            val visited = front.toList().fold(HashMap<Node, Int>()) { acc, node ->
                acc[node] = acc.getOrDefault(node, 0) + 1
                acc
            }

            front.last().edges.forEach {
                val isLowerCase = it.value.all { c -> c.isLowerCase() }
                val isUpperCase = !isLowerCase

                val hasLowercaseRepeated = visited.filter { entry -> entry.key.value.all { v -> v.isLowerCase() } }
                    .any { entry -> entry.value > 1 }

                val isStart = it.value == "start"

                if ((isLowerCase && !visited.contains(it)) || (!isStart && isLowerCase && !hasLowercaseRepeated) || isUpperCase) {
                    val newPath = ArrayList(front)
                    newPath.add(it)
                    queue.add(newPath)
                }
            }
        }

        return paths.size
    }

    val testInput = readInput("Day12_test")
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part2(input))
}