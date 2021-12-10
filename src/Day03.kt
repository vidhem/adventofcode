fun main() {
    fun part1(input: List<String>): Int {
        val bitCounts: ArrayList<HashMap<String, Int>> = ArrayList()
        repeat(input[0].split("").size-2) {
            bitCounts.add(HashMap())
        }

        input.forEach {
            it.split("").drop(1).dropLast(1).forEachIndexed { index, bit ->
                bitCounts[index][bit] = bitCounts[index].getOrDefault(bit, 0) + 1
            }
        }

        val gamma = bitCounts.fold("") { acc, counts -> acc + counts.maxByOrNull { it.value }!!.key }
            .toInt(2)

        val epsilon = bitCounts.fold("") { acc, counts -> acc + counts.minByOrNull { it.value }!!.key }
            .toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}