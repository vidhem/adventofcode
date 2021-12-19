package aoc2021

import kotlin.math.max

fun main() {

    fun getPossibleVels(input: String): Set<Pair<Int, Int>> {
        val targetParts = input.substring("target area: ".length).split(", ")
        val ranges = targetParts.map {
            it.split("=")[1]
                .split("..")
                .map { it.toInt() }
        }

        val vels = HashSet<Pair<Int, Int>>()
        for (x in 1..ranges[0][1]) {
            for (y in -1000..1000) {
                var vel = Pair(x, y)
                var pos = Pair(0, 0)

                while (true) {
                    if (pos.first >= ranges[0][0]
                        && pos.first <= ranges[0][1]
                        && pos.second >= ranges[1][0]
                        && pos.second <= ranges[1][1]) {
                        vels.add(Pair(x, y))
                        break
                    }

                    if (pos.second < ranges[1][0] || x > ranges[0][1]) {
                        break
                    }

                    pos = Pair(pos.first + vel.first, pos.second + vel.second)
                    vel = Pair(max(vel.first - 1, 0), vel.second - 1)
                }
            }
        }

        return vels.toSet()
    }

    fun part1(input: String): Int {
        val maxYVel = getPossibleVels(input).maxOf { it.second }
        return maxYVel * (maxYVel + 1) / 2
    }

    fun part2(input: String): Int {
        return getPossibleVels(input).size
    }

    val testInput = readInput("Day17_test")[0]
    println(part2(testInput))

    val input = readInput("Day17")[0]
    println(part1(input))
    println(part2(input))
}
