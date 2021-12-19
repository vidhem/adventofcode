package aoc2021

fun main() {

    fun getVersionSum(binary: String): Pair<Int, Int> {
        var versionSum = 0
        var pointer = 0

        val remainingPacket = binary.substring(pointer)
        if (remainingPacket.isEmpty() || remainingPacket.all { it == '0' }) {
            return Pair(versionSum, pointer)
        }

        versionSum += binary.substring(pointer, pointer + 3).toInt(2)
        pointer += 3
        val typeId = binary.substring(pointer, pointer + 3).toInt(2)
        pointer += 3

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

            return Pair(versionSum, pointer)
        }

        val operatorType = binary[pointer]
        pointer += 1

        if (operatorType == '0') {
            val subPacketLength = binary.substring(pointer, pointer + 15).toInt(2)
            pointer += 15

            var totalLength = 0
            while (totalLength < subPacketLength) {
                val versionSumAndPosition = getVersionSum(binary.substring(pointer))
                versionSum += versionSumAndPosition.first

                totalLength += versionSumAndPosition.second
                pointer += versionSumAndPosition.second
            }

            return Pair(versionSum, pointer)
        }

        if (operatorType == '1') {
            val numPackets = binary.substring(pointer, pointer + 11).toInt(2)
            pointer += 11

            repeat(numPackets) {
                val versionSumAndPosition = getVersionSum(binary.substring(pointer))
                versionSum += versionSumAndPosition.first
                pointer += versionSumAndPosition.second
            }

            return Pair(versionSum, pointer)
        }

        return Pair(versionSum, pointer)
    }

    fun part1(input: List<String>): Int {
        val binary = input[0].toCharArray().joinToString("") {
            it.digitToInt(16)
                .toString(2)
                .padStart(4, '0')
        }

        return getVersionSum(binary).first
    }

    val testInput = readInput("Day16_test")
    println(part1(testInput))

    val input = readInput("Day16")
    println(part1(input))
}