package aoc2021

fun main() {
    fun part1(input: List<String>): Int {
        val points: MutableList<Pair<Int,Int>> = ArrayList()
        val folds: MutableList<Pair<Char,Int>> = ArrayList()

        input.forEach {
            if (it.trim().isEmpty()) {
                return@forEach
            }
            if (it.startsWith("fold along")) {
                val fold = it.split(" ")[2].split("=")
                folds.add(Pair(fold[0][0], fold[1].toInt()))
                return@forEach
            }
            points.add(Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt()))
        }

        var grid = MutableList(points.maxOf { it.second } + 1) { MutableList(points.maxOf { it.first } + 1) { '.' } }
        points.forEach { grid[it.second][it.first] = '#' }

        val firstFold = folds[0]
        if (firstFold.first == 'y') {
            val ymax = firstFold.second

            grid.drop(ymax+1).forEachIndexed { yindex, row ->
                row.forEachIndexed { xindex, dot ->
                    if (dot == '#') {
                        grid[ymax - yindex - 1][xindex] = '#'
                    }
                }
            }

            grid = grid.take(ymax).toMutableList()
        } else {
            val xmax = firstFold.second

            grid.forEachIndexed { yindex, row ->
                row.drop(xmax+1).forEachIndexed { xindex, dot ->
                    if (dot == '#') {
                        grid[yindex][xmax - xindex - 1] = '#'
                    }
                }
            }

            grid = grid.map { it.take(xmax).toMutableList() }.toMutableList()
        }

        return grid.sumOf { it.count { el -> el == '#' } }
    }

    fun part2(input: List<String>) {
        val points: MutableList<Pair<Int,Int>> = ArrayList()
        val folds: MutableList<Pair<Char,Int>> = ArrayList()

        input.forEach {
            if (it.trim().isEmpty()) {
                return@forEach
            }
            if (it.startsWith("fold along")) {
                val fold = it.split(" ")[2].split("=")
                folds.add(Pair(fold[0][0], fold[1].toInt()))
                return@forEach
            }
            points.add(Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt()))
        }

        var grid = MutableList(points.maxOf { it.second } + 1) { MutableList(points.maxOf { it.first } + 1) { ' ' } }
        points.forEach { grid[it.second][it.first] = '#' }

        folds.forEach { fold ->
            if (fold.first == 'y') {
                val ymax = fold.second

                grid.drop(ymax + 1).forEachIndexed { yindex, row ->
                    row.forEachIndexed { xindex, dot ->
                        if (dot == '#') {
                            grid[ymax - yindex - 1][xindex] = '#'
                        }
                    }
                }

                grid = grid.take(ymax).toMutableList()
            } else {
                val xmax = fold.second

                grid.forEachIndexed { yindex, row ->
                    row.drop(xmax + 1).forEachIndexed { xindex, dot ->
                        if (dot == '#') {
                            grid[yindex][xmax - xindex - 1] = '#'
                        }
                    }
                }

                grid = grid.map { it.take(xmax).toMutableList() }.toMutableList()
            }
        }

        grid.forEach { println(it) }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}