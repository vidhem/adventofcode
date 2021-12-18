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

    fun flashGrid(x: Int, y: Int, newGrid: MutableList<MutableList<Int>>) {
        if (x > 0) {
            newGrid[y][x-1] += 1
            if (y < newGrid.size - 1) {
                newGrid[y+1][x-1] += 1
            }
            if (y > 0) {
                newGrid[y-1][x-1] += 1
            }
        }
        if (y > 0) {
            newGrid[y-1][x] += 1
        }
        if (x < newGrid[0].size - 1) {
            newGrid[y][x+1] += 1
            if (y > 0) {
                newGrid[y-1][x+1] += 1
            }
            if (y < newGrid.size - 1) {
                newGrid[y+1][x+1] += 1
            }
        }
        if (y < newGrid.size - 1) {
            newGrid[y+1][x] += 1
        }
    }

    fun part1(input: List<String>): Int {
        var grid = input.map {
            it.toCharArray().map { c -> c.digitToInt() }.toMutableList()
        }.toMutableList()

        var totalFlashes = 0
        repeat(100) {
            grid.forEach { row -> row.mapInPlace { it + 1 } }
            val flashed = HashSet<Pair<Int, Int>>()

            while (grid.flatten().any { it >= 10 }) {
                val newGrid = ArrayList(grid)

                grid.forEachIndexed { y, row ->
                    row.forEachIndexed { x, energy ->
                        if (energy >= 10 && !flashed.contains(Pair(x,y))) {
                            totalFlashes += 1
                            newGrid[y][x] = 0
                            flashGrid(x, y, newGrid)
                            flashed.add(Pair(x,y))
                        }
                    }
                }

                flashed.forEach { newGrid[it.second][it.first] = 0 }
                grid = newGrid
            }
        }

        return totalFlashes
    }

    fun part2(input: List<String>): Int {
        var grid = input.map {
            it.toCharArray().map { c -> c.digitToInt() }.toMutableList()
        }.toMutableList()

        var totalFlashes = 0
        repeat(1000) {
            grid.forEach { row -> row.mapInPlace { it + 1 } }
            val flashed = HashSet<Pair<Int, Int>>()

            while (grid.flatten().any { it >= 10 }) {
                val newGrid = ArrayList(grid)

                grid.forEachIndexed { y, row ->
                    row.forEachIndexed { x, energy ->
                        if (energy >= 10 && !flashed.contains(Pair(x,y))) {
                            totalFlashes += 1
                            newGrid[y][x] = 0
                            flashGrid(x, y, newGrid)
                            flashed.add(Pair(x,y))
                        }
                    }
                }

                flashed.forEach { newGrid[it.second][it.first] = 0 }
                grid = newGrid
            }

            if (grid.flatten().all { el -> el == 0 }) {
                return it + 1
            }
        }

        return -1
    }

    val testInput = readInput("Day11_test")
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}