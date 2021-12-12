package aoc2021

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

    fun getBitCounts(input: List<String>): ArrayList<HashMap<String, Int>> {
        val bitCounts: ArrayList<HashMap<String, Int>> = ArrayList()
        val size = input[0].split("").size-2
        repeat(size) {
            bitCounts.add(HashMap())
        }

        input.forEach {
            it.split("").drop(1).dropLast(1).forEachIndexed { index, bit ->
                bitCounts[index][bit] = bitCounts[index].getOrDefault(bit, 0) + 1
            }
        }

        return bitCounts
    }

    val comparator = Comparator<Map.Entry<String, Int>> { o1, o2 ->
        when (o1.value) {
            o2.value -> if (o1.key == "1") 1 else -1
            else -> o1.value - o2.value
        }
    }

    fun part2(input: List<String>): Int {
        val numBits = input[0].split("").size - 2
        val oxygenNumbers = ArrayList(input)
        for (index in 0..numBits) {
            val bitCounts = getBitCounts(oxygenNumbers)
            val maxBits = bitCounts.map { counts -> counts.maxWithOrNull(comparator) !!.key }

            oxygenNumbers.retainAll {
                (it.toInt(2) and (1 shl (numBits - index - 1))
                        xor (maxBits[index].toInt(2) shl (numBits - index - 1))) == 0
            }

            if (oxygenNumbers.size == 1) {
                break
            }
        }

        val co2Numbers = ArrayList(input)
        for (index in 0..numBits) {
            val bitCounts = getBitCounts(co2Numbers)
            val minBits = bitCounts.map { counts -> counts.minWithOrNull(comparator)!!.key }

            co2Numbers.retainAll {
                (it.toInt(2) and (1 shl (numBits - index - 1))
                        xor (minBits[index].toInt(2) shl (numBits - index - 1))) == 0
            }

            if (co2Numbers.size == 1) {
                break
            }
        }

        return oxygenNumbers[0].toInt(2) * co2Numbers[0].toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}