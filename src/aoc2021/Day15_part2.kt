package aoc2021

fun main() {

    fun findPath(input: List<List<Int>>, memoize: MutableList<MutableList<Int>>, x: Int = 0, y: Int = 0): Int {
        if (memoize[y][x] != -1) {
            return memoize[y][x]
        }

        if (x < input[0].size - 1 && y < input.size - 1) {
            memoize[y][x] = input[y][x] + minOf(findPath(input, memoize, x+1, y), findPath(input, memoize, x, y+1))
        } else if (x < input[0].size - 1) {
            memoize[y][x] = input[y][x] + findPath(input, memoize, x+1, y)
        } else if (y < input.size - 1) {
            memoize[y][x] = input[y][x] + findPath(input, memoize, x, y+1)
        } else {
            memoize[y][x] = input[y][x]
        }

        return memoize[y][x]
    }

    fun part2(input: List<String>): Int {
        val grid: MutableList<MutableList<Int>> = ArrayList()

        val parsedInput = input.map { it.toCharArray().map { c -> c.digitToInt() } }
        var tile = parsedInput.toTypedArray().copyOf().toList()

        repeat(5) {
            tile.forEach { grid.add(it.toMutableList()) }
            tile = tile.map { it.map { d ->
                val new = d + 1
                if (new > 9) 1 else new
            }}
        }

        repeat(4) { index ->
            grid.forEach { row ->
                row.addAll(row.subList(index * input.size, (index + 1) * input.size).map {
                    if (it + 1 > 9) 1 else it + 1
                })
            }
        }

        val memoize = MutableList(5 * grid.size) { MutableList(5 * grid[0].size) { -1 } }
        return findPath(grid, memoize) - grid[0][0]
    }

    val testInput = readInput("Day15_test")
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part2(input))
}