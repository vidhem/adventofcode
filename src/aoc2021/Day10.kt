package aoc2021

fun main() {
    fun getPenalty(symbol: Char): Int {
        return when (symbol) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val stack = ArrayDeque<Char>()
            var score = 0

            for (symbol in line.toCharArray()) {
                if (symbol == '(' || symbol == '[' || symbol == '{' || symbol == '<') {
                    stack.addFirst(symbol)
                    continue
                }

                val top = stack.first()
                if ((top == '(' && symbol != ')')
                    || (top == '[' && symbol != ']')
                    || (top == '{' && symbol != '}')
                    || (top == '<' && symbol != '>')) {
                    score = getPenalty(symbol)
                    break
                }

                stack.removeFirst()
            }

            acc + score
        }
    }

    fun getAutocompleteScore(symbol: Char): Int {
        return when (symbol) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
    }

    fun part2(input: List<String>): Long {
        val scores = ArrayList<Long>()
        input.forEach { line ->
            val stack = ArrayDeque<Char>()

            for (symbol in line.toCharArray()) {
                if (symbol == '(' || symbol == '[' || symbol == '{' || symbol == '<') {
                    stack.addFirst(symbol)
                    continue
                }

                val top = stack.first()
                if ((top == '(' && symbol != ')')
                    || (top == '[' && symbol != ']')
                    || (top == '{' && symbol != '}')
                    || (top == '<' && symbol != '>')) {

                    return@forEach
                }

                stack.removeFirst()
            }

            val score = stack.toArray().map {
                when (it) {
                    '(' -> ')'
                    '[' -> ']'
                    '{' -> '}'
                    '<' -> '>'
                    else -> '#'
                }
            }.fold(0L) { acc, it -> acc * 5 + getAutocompleteScore(it) }

            scores.add(score)
        }

        scores.sort()
        return scores[(scores.size - 1)/2]
    }

    val testInput = readInput("Day10_test")
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}