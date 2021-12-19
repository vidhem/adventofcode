package aoc2021

fun main() {

    fun operate(typeId: Int, values: List<Long>): Long {
        return when (typeId) {
            0 -> values.sum()
            1 -> values.fold(1) { acc, it -> acc * it }
            2 -> values.minOf { it }
            3 -> values.maxOf { it }
            5 -> {
                check(values.size == 2)
                if (values[0] > values[1]) 1 else 0
            }
            6 -> {
                check(values.size == 2)
                if (values[0] < values[1]) 1 else 0
            }
            else -> {
                check(values.size == 2)
                if (values[0] == values[1]) 1 else 0
            }
        }
    }

    fun getValue(binary: String): Pair<Long, Int> {
        var pointer = 0

        val remainingPacket = binary.substring(pointer)
        if (remainingPacket.isEmpty() || remainingPacket.all { it == '0' }) {
            return Pair(0, pointer)
        }

        pointer += 3
        val typeId = binary.substring(pointer, pointer + 3).toInt(2)
        pointer += 3

        if (typeId == 4) {
            val valueBuilder = StringBuilder()
            run {
                remainingPacket.substring(6)
                    .chunked(5)
                    .forEach {
                        pointer += 5
                        valueBuilder.append(it.drop(1))
                        if (it[0] == '0') {
                            return@run
                        }
                    }
            }

            return Pair(valueBuilder.toString().toLong(2), pointer)
        }

        val operatorType = binary[pointer]
        pointer += 1

        if (operatorType == '0') {
            val subPacketLength = binary.substring(pointer, pointer + 15).toInt(2)
            pointer += 15

            val packetValues = ArrayList<Long>()
            var totalLength = 0
            while (totalLength < subPacketLength) {
                val versionSumAndPosition = getValue(binary.substring(pointer))
                packetValues.add(versionSumAndPosition.first)

                totalLength += versionSumAndPosition.second
                pointer += versionSumAndPosition.second
            }

            return Pair(operate(typeId, packetValues), pointer)
        }

        if (operatorType == '1') {
            val numPackets = binary.substring(pointer, pointer + 11).toInt(2)
            pointer += 11

            val packetValues = ArrayList<Long>()
            repeat(numPackets) {
                val valueAndPosition = getValue(binary.substring(pointer))
                packetValues.add(valueAndPosition.first)
                pointer += valueAndPosition.second
            }

            return Pair(operate(typeId, packetValues), pointer)
        }

        return Pair(0, pointer)
    }

    fun part2(input: List<String>): Long {
        val binary = input[0].toCharArray().joinToString("") {
            it.digitToInt(16)
                .toString(2)
                .padStart(4, '0')
        }

        return getValue(binary).first
    }

    val testInput = readInput("Day16_test")
    println(part2(testInput))

    val input = readInput("Day16")
    println(part2(input))
}