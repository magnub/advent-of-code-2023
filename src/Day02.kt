import java.io.File
import kotlin.math.max

fun main() {

    fun part1(lines: List<String>) {
        val config = Config()
        val results = mutableSetOf<Int>()
        lines.forEach {
            val noWhiteSpace = it.filterNot { it.isWhitespace() }.split("Game", ";", ",", ":")
            val id = noWhiteSpace[1].toInt()
            for (i in 2..<noWhiteSpace.size) {
                if (noWhiteSpace[i].countIsValid(config)) {
                    results.add(id)
                } else {
                    results.remove(id)
                    break
                }
            }
        }
        println(results.sum())
    }

    fun part2(lines: List<String>) {
        val colors = setOf("red", "green", "blue")
        var sum = 0
        lines.forEach {
            val noWhiteSpace = it.filterNot { it.isWhitespace() }.split("Game", ";", ",", ":")
            val map = hashMapOf<String, Int>()
            for (i in 2..<noWhiteSpace.size) {
                colors.filter { noWhiteSpace[i].contains(it) }
                    .map { map[it] = max(noWhiteSpace[i].replaceToInt(it), map[it] ?: 0) }
            }
            sum += map.values.reduce { acc, value -> acc * value }
        }
        println(sum)
    }

    val lines = File("/Users/mange/Downloads/advent23/2").readLines()
    part1(lines)
    part2(lines)
}

data class Config(
    val red: Int = 12,
    val green: Int = 13,
    val blue: Int = 14,
)

fun String.countIsValid(config: Config): Boolean {
    return when {
        this.contains("red") -> this.replaceToInt("red") <= config.red
        this.contains("green") -> this.replaceToInt("green") <= config.green
        this.contains("blue") -> this.replaceToInt("blue") <= config.blue
        else -> {
            false
        }
    }
}

fun String.replaceToInt(replace: String, newValue: String = "") = this.replace(replace, newValue).trim().toInt()
