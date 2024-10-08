import java.io.File

fun main() {

    fun sum(lines: List<String>): Int {
        var sum = 0
        lines.forEach {
            val digits = it.filter { it.isDigit() }
            sum += "${digits.first()}${digits.last()}".toInt()
        }
        return sum
    }

    fun part1(lines: List<String>) {
        println(sum(lines))
    }

    fun part2(lines: List<String>) {
        val list = mutableListOf<String>()
        lines.forEach {
            val windowed = it.windowed(5, 1, true)
            var result = ""
            windowed.forEach { each ->
                each.trim().toNumber().filterNot { it.isWhitespace() || it.toString().isEmpty() }.let {
                    result += it
                }
            }
            list.add(result)
        }
        println(sum(list))
    }

    val lines = readInput("Day1")
    part1(lines)
    part2(lines)
}

val numbers = setOf(
    "zero" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun String.toNumber(): String {
    numbers.forEach {
        if (this.contains(it.first)) {
            return this.replace(it.first, it.second)
        }
    }
    if (this.any { it.isDigit() }) {
        return this
    }
    return ""
}

