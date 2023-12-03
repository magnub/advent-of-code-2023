import java.io.File

fun main() {
    val lines = File("/Users/mange/Downloads/advent23/1").readLines()
    part1(lines)
    part2(lines)
}

fun sum(lines: List<String>): Int {
    var sum = 0
    lines.forEach {
        if (it.isEmpty()) return@forEach
        val first = it.first { it.isDigit() }
        val last = it.last { it.isDigit() }
        sum += "$first$last".toInt()
    }
    return sum
}

fun part1(lines: List<String>) {
    println(sum(lines))
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

