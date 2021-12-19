package aoc2021

fun main() {

    fun getVersionSum(binary: String): Int {
        var versionSum = 0
        var pointer = 0

        while (true) {
            val remainingPacket = binary.substring(pointer)
            if (remainingPacket.isEmpty() || remainingPacket.all { it == '0' }) {
                break
            }

            versionSum += remainingPacket.substring(0, 3).toInt(2)
            val typeId = remainingPacket.substring(3, 6).toInt(2)
            pointer += 6

            if (typeId == 4) {
                run {
                    remainingPacket.substring(6)
                        .chunked(5)
                        .forEach {
                            pointer += 5
                            if (it[0] == '0') {
                                return@run
                            }
                        }
                }
            }
        }

        return versionSum
    }

    fun part1(input: List<String>): Int {
        val binary = input[0].toCharArray().joinToString("") {
            it.digitToInt(16)
                .toString(2)
                .padStart(4, '0')
        }

        return getVersionSum(binary)
    }

    val testInput = readInput("Day16_test")
    println(part1(testInput))
}