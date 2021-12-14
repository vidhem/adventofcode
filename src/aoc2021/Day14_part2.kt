package aoc2021

fun main() {

    fun part2(input: List<String>): Long {
        val rules = input.drop(2)
            .map { it.split(" -> ") }

        var pairs = HashMap<String, Long>()
        input[0].toCharArray().toList().windowed(2).forEach { pair ->
            val (c1, c2) = pair
            val pairString = "$c1$c2"
            pairs[pairString] = pairs.getOrDefault(pairString, 0) + 1
        }

        repeat(40) {
            val newPairs = HashMap<String, Long>()
            rules.forEach { rule ->
                val (source, target) = rule
                val (c1, c2) = source.toCharArray()

                if (pairs.contains(source)) {
                    val pairCount = pairs[source] ?: 0

                    val newPairFirst = "$c1$target"
                    val newPairSecond = "$target$c2"
                    newPairs[newPairFirst] = (newPairs[newPairFirst] ?: 0) + pairCount
                    newPairs[newPairSecond] = (newPairs[newPairSecond] ?: 0) + pairCount
                }
            }
            pairs = newPairs
        }

        val doubleCounts = pairs.entries.fold(HashMap<Char, Long>()) { acc, it ->
            acc[it.key[0]] = (acc[it.key[0]] ?: 0) + it.value
            acc[it.key[1]] = (acc[it.key[1]] ?: 0) + it.value
            acc
        }

        // Ends are single counted, rest double counted
        doubleCounts[input[0].first()] = doubleCounts[input[0].first()]!! + 1
        doubleCounts[input[0].last()] = doubleCounts[input[0].last()]!! + 1

        val max = doubleCounts.maxOf { it.value } / 2
        val min = doubleCounts.minOf { it.value } / 2

        return (max - min)
    }

    val testInput = readInput("Day14_test")
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part2(input))
}