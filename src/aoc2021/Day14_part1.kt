package aoc2021

fun main() {

    fun part1(input: List<String>): Int {
        val rules = input.drop(2)
            .map { it.split(" -> ") }

        var template = input[0].toCharArray().toMutableList()
        repeat(10) {
            val iterationResult: MutableList<Char> = ArrayList()
            template.windowed(2).forEach { window ->
                val (c1, c2) = window
                iterationResult.add(c1)
                val matchedRule = rules.find { it[0][0] == c1 && it[0][1] == c2 }
                if (matchedRule != null) {
                    iterationResult.add(matchedRule[1][0])
                }
            }
            iterationResult.add(template.last())

            template = iterationResult
        }

        val counts = template.fold(HashMap<Char, Int>()) { acc, c ->
            acc[c] = acc.getOrDefault(c, 0) + 1
            acc
        }

        return (counts.maxByOrNull { it.value }!!.value - counts.minByOrNull { it.value }!!.value)
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)

    val input = readInput("Day14")
    println(part1(input))
}