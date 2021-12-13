package aoc2021

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Node(val value: String) {
    val edges: MutableSet<Node> = HashSet()

    override fun toString(): String {
        return value
    }
}

fun main() {

    fun part1(input: List<String>): Int {
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

            val visited = front.toSet()

            front.last().edges.forEach {
                if ((it.value.all { c -> c.isLowerCase() } && !visited.contains(it))
                    || it.value.all { c -> c.isUpperCase() }) {

                    val newPath = ArrayList(front)
                    newPath.add(it)
                    queue.add(newPath)
                }
            }
        }

        return paths.size
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)

    val input = readInput("Day12")
    println(part1(input))
}