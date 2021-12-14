package aoc2021

inline fun <T> MutableList<T>.mapInPlace(mutator: (T)->T) {
    val iterate = this.listIterator()
    while (iterate.hasNext()) {
        val oldValue = iterate.next()
        val newValue = mutator(oldValue)
        if (newValue !== oldValue) {
            iterate.set(newValue)
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val grid = input.map {
            it.toCharArray().map { c -> c.digitToInt() }.toMutableList()
        }.toMutableList()

        repeat(1) {
            grid.forEach { row -> row.mapInPlace { it + 1 } }

            grid.forEachIndexed { xindex, row ->
                row.forEachIndexed { yindex, energy ->
                }
            }
        }

        return 0
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
}