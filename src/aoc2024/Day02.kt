package aoc2024

import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val reports = input.map { it.split(" ").map { level -> level.toInt() } }
        return reports.fold(0) { acc, report ->
            val increasing = report[1] > report[0]
            for (i in 1 until report.size) {
                if ((report[i] > report[i - 1]) != increasing) {
                    return@fold acc
                }

                val absDiff = abs(report[i] - report[i-1])
                if (absDiff > 3 || absDiff < 1) {
                    return@fold acc
                }
            }

            acc+1
        }
    }

    fun part2(input: List<String>): Int {
        fun checkValid(report: List<Int>): Boolean {
            val increasing = report[1] > report[0]
            for (i in 1 until report.size) {
                if ((report[i] > report[i - 1]) != increasing) {
                    return false
                }

                val absDiff = abs(report[i] - report[i-1])
                if (absDiff > 3 || absDiff < 1) {
                    return false
                }
            }

            return true
        }

        val reports = input.map { it.split(" ").map { level -> level.toInt() } }

        return reports.fold(0) { acc, report ->
            var isValid = checkValid(report)
            for (i in report.indices) {
                if (isValid) {
                    break
                }

                val mutableReport = report.toMutableList()
                val newReport = mutableReport.subList(0, i) + mutableReport.subList(i+1, mutableReport.size)
                isValid = checkValid(newReport)
            }

            return@fold if (isValid) acc+1 else acc
        }
    }

    val input = readInput("Day02")
    println(part2(input))
}