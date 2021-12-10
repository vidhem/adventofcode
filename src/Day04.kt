fun main() {
    fun part1(input: List<String>): Int {
        val order = input[0].split(',').map { it.toInt() }
        val numBoards: Int = (input.size - 1) / 6
        val boards: ArrayList<ArrayList<HashSet<Int>>> = ArrayList()

        repeat(numBoards) { board ->
            val lines: ArrayList<List<Int>> = ArrayList()
            for (line in input.subList(2 + 6 * board, 2 + 6 * (board + 1) - 1)) {
                lines.add(
                    line.trim()
                        .split(" ")
                        .filter { it.trim().isNotEmpty() }
                        .map { it.toInt() }
                )
            }

            val wins = ArrayList<HashSet<Int>>()
            wins.addAll(lines.map { it.toHashSet() })
            repeat(5) { index ->
                wins.add(
                    lines.fold(ArrayList<Int>()) { acc, it -> ArrayList(acc + it[index]) }.toHashSet()
                )
            }
            boards.add(wins)
        }

        for (number in order) {
            for (board in boards) {
                var won = false
                for (combination in board) {
                    combination.remove(number)
                    if (combination.isEmpty()) {
                        won = true
                    }
                }
                if (won) {
                    return number * board.flatten().toSet().sum()
                }
            }
        }

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}