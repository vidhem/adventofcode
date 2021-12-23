package aoc2021

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

internal data class SnailfishNumber(var value: Int?,
                                    var left: SnailfishNumber? = null,
                                    var right: SnailfishNumber? = null) {

    companion object {
        fun from(rawNumber: JsonArray): SnailfishNumber {
            val first = rawNumber.get(0)
            val second = rawNumber.get(1)

            val left: SnailfishNumber = if (first is JsonPrimitive) {
                SnailfishNumber(first.asInt)
            } else {
                from(first.asJsonArray)
            }

            val right: SnailfishNumber = if (second is JsonPrimitive) {
                SnailfishNumber(second.asInt)
            } else {
                from(second.asJsonArray)
            }

            return SnailfishNumber(null, left, right)
        }
    }

    fun getInorderRepresentation(level: Int = 0): List<Pair<SnailfishNumber, Int>> {
        if (this.value != null) {
            return listOf(Pair(this, level))
        }

        val repr = ArrayList<Pair<SnailfishNumber, Int>>()

        Optional.ofNullable(this.left).ifPresent { repr.addAll(it.getInorderRepresentation(level+1)) }
        repr.add(Pair(this, level))
        Optional.ofNullable(this.right).ifPresent { repr.addAll(it.getInorderRepresentation(level+1)) }

        return repr
    }

    override fun toString(): String {
        return value.toString()
    }
}

internal fun explode(number: SnailfishNumber): Boolean {
    val inorder = number.getInorderRepresentation()
    for (partIndex in inorder.indices) {
        val part = inorder[partIndex]
        val level = part.second
        if (level != 5) {
            continue
        }

        val parentInorderIndex = partIndex + 1
        val parent = inorder[parentInorderIndex].first

        for (index in parentInorderIndex+2 until inorder.size) {
            if (inorder[index].first.value != null) {
                val literal = inorder[index].first
                literal.value = literal.value!! + parent.right!!.value!!
                break
            }
        }

        for (index in parentInorderIndex-2 downTo 0) {
            if (inorder[index].first.value != null) {
                val literal = inorder[index].first
                literal.value = literal.value!! + parent.left!!.value!!
                break
            }
        }

        parent.left = null
        parent.right = null
        parent.value = 0

        return true
    }

    return false
}

internal fun split(number: SnailfishNumber): Boolean {
    number.getInorderRepresentation().forEach {
        val node = it.first
        if ((node.value ?: -1) >= 10) {
            val value = node.value!!
            node.value = null
            node.left = SnailfishNumber(floor(value / 2.0).toInt())
            node.right = SnailfishNumber(ceil(value / 2.0).toInt())

            return true
        }
    }

    return false
}

internal fun reduce(number: SnailfishNumber): Boolean {
    return if (explode(number)) {
        true
    } else split(number)

}

internal fun magnitude(number: SnailfishNumber): Int {
    if (number.value != null) {
        return number.value!!
    }

    return 3 * magnitude(number.left!!) + 2 * magnitude(number.right!!)
}

internal fun add(a: SnailfishNumber, b: SnailfishNumber): SnailfishNumber {
    val number = SnailfishNumber(null, a, b)
    while (reduce(number)) {}
    return number
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { JsonParser.parseString(it).asJsonArray }
            .map { SnailfishNumber.from(it) }

        val additionResult = numbers.drop(1).fold(numbers[0]) { acc, it -> add(acc, it) }

        return magnitude(additionResult)
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { JsonParser.parseString(it).asJsonArray }
            .map { SnailfishNumber.from(it) }

        var maxAddMagnitude = Int.MIN_VALUE
        for (i in input.indices) {
            for (j in input.indices) {
                val a = SnailfishNumber.from(JsonParser.parseString(input[i]).asJsonArray)
                val c = SnailfishNumber.from(JsonParser.parseString(input[i]).asJsonArray)

                val b = SnailfishNumber.from(JsonParser.parseString(input[j]).asJsonArray)
                val d = SnailfishNumber.from(JsonParser.parseString(input[j]).asJsonArray)

                maxAddMagnitude = maxOf(maxAddMagnitude, magnitude(add(a,b)), magnitude(add(c,d)))
            }
        }

        return maxAddMagnitude
    }

    val testInput = readInput("Day18_test")
    println(part2(testInput))

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
