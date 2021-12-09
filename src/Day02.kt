import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        input.forEach {
            val (direction, amount) = it.split(" ")
            when (direction) {
                "forward" -> {
                    x += amount.toInt()
                }
                "down" -> {
                    y -= amount.toInt()
                }
                "up" -> {
                    y += amount.toInt()
                }
            }
        }
        return x * abs(y)
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        var aim = 0
        input.forEach {
            val (direction, amount) = it.split(" ")
            when (direction) {
                "forward" -> {
                    x += amount.toInt()
                    y += aim * amount.toInt()
                }
                "down" -> {
                    aim -= amount.toInt()
                }
                "up" -> {
                    aim += amount.toInt()
                }
            }
        }
        return x * abs(y)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}