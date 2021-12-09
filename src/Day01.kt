import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var increases = 0
        var last = input[0].toInt()

        input.map { it.toInt() }.forEach {
            if (it > last) {
                increases++;
            }
            last = it
        }

        return increases
    }

    fun part2(input: List<String>): Int {
        val window = LinkedList<Int>()
        val depths = input.map { it.toInt() }

        for (i in 0..2) {
            window.add(depths[i])
        }

        var sum = window.reduce { acc, i -> acc + i }
        var increases = 0
        for (i in 3 until depths.size) {
            val newSum = sum - window.peek() + depths[i]
            if (newSum > sum) {
                increases++
            }

            sum = newSum
            window.remove()
            window.add(depths[i])
        }

        return increases
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
