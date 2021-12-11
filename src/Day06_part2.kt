import java.util.*

fun main() {
    fun part2(input: List<String>): Long {
        val counts = MutableList(9) { 0L }
        input[0].split(",").forEach { counts[it.toInt()]++ }

        repeat(256) {
            Collections.rotate(counts, -1)
            counts[6] += counts[8]
        }

        return counts.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part2(input))
}