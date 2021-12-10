fun main() {
    fun readBoards(input: List<String>): List<List<MutableSet<Int>>> {
        val numBoards: Int = (input.size - 1) / 6
        val boards = ArrayList<List<MutableSet<Int>>>()

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

        return boards
    }

    fun part1(input: List<String>): Int {
        val order = input[0].split(',').map { it.toInt() }
        val boards = readBoards(input)

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
        val order = input[0].split(',').map { it.toInt() }
        var boards = readBoards(input)

        for (number in order) {
            val newBoards = ArrayList(boards)
            val indexesToRemove = ArrayList<Int>()

            boards.forEachIndexed { index, board ->
                for (combination in board) {
                    combination.remove(number)
                    if (combination.isEmpty()) {
                        indexesToRemove.add(index)
                        if (newBoards.size == 1) {
                            val remainingNumbers = newBoards[0].flatten().toMutableSet()
                            remainingNumbers.remove(number)

                            return number * remainingNumbers.sum()
                        }
                        break
                    }
                }
            }

            indexesToRemove.reversed().forEach { newBoards.removeAt(it) }
            boards = newBoards
        }

        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}