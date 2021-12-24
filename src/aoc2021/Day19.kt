package aoc2021

fun main() {

    fun refine(image: List<List<Char>>, algorithm: List<Char>): List<List<Char>> {
        fun toBit(pixel: Char): Char {
            return if (pixel == '.') '0' else '1'
        }

        val newImage = MutableList(image.size) { MutableList(image[0].size) { '.' } }

        for (y in 1 until image.size-1) {
            for (x in 1 until image[0].size-1) {
                val bits = ArrayList<Char>()
                bits.add(toBit(image[y-1][x-1]))
                bits.add(toBit(image[y-1][x]))
                bits.add(toBit(image[y-1][x+1]))
                bits.add(toBit(image[y][x-1]))
                bits.add(toBit(image[y][x]))
                bits.add(toBit(image[y][x+1]))
                bits.add(toBit(image[y+1][x-1]))
                bits.add(toBit(image[y+1][x]))
                bits.add(toBit(image[y+1][x+1]))

                newImage[y][x] = algorithm[bits.joinToString("").toInt(2)]
            }
        }

        return newImage
    }

    fun enhance(reps: Int, algorithm: List<Char>, image: List<List<Char>>): List<List<Char>> {
        val padding = 100

        val infiniteImage = MutableList(image.size + 2 * padding) {
            MutableList(image[0].size + 2 * padding) { '.' }
        }

        for (y in padding until padding + image.size) {
            for (x in padding until padding + image[0].size) {
                infiniteImage[y][x] = image[y-padding][x-padding]
            }
        }

        var refined: List<List<Char>> = infiniteImage.map { it.toList() }
        repeat(reps) {
            refined = refine(refined, algorithm)
        }

        return refined
    }

    fun part1(input: List<String>): Int {
        val algorithm = input[0].toCharArray().toList()
        val originalImage = input.drop(2).map { it.toCharArray().toList() }

        val reps = 2
        val enhanced = enhance(reps, algorithm, originalImage)

        return enhanced.drop(reps).dropLast(reps)
            .map { it.drop(reps).dropLast(reps) }
            .sumOf { it.sumOf { c -> if (c == '.') 0L else 1L }.toInt() }
    }

    fun part2(input: List<String>): Int {
        val algorithm = input[0].toCharArray().toList()
        val originalImage = input.drop(2).map { it.toCharArray().toList() }

        val reps = 50
        val enhanced = enhance(reps, algorithm, originalImage)

        return enhanced.drop(reps).dropLast(reps)
            .map { it.drop(reps).dropLast(reps) }
            .sumOf { it.sumOf { c -> if (c == '.') 0L else 1L }.toInt() }
    }

    val testInput = readInput("Day19_test")
    check(part2(testInput) == 3351)

    val input = readInput("Day19")
    println(part1(input))
    println(part2(input))
}