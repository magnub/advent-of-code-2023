import java.io.File
import kotlin.math.pow
import kotlin.time.measureTimedValue

class Card(var count: Int = 1, var idx: Int) {

    fun increment(withAmount: Int = 1) {
        count += withAmount
    }

    override fun toString(): String = "Card ${1+idx}: $count"
}

fun main() {

    fun part1(lines: List<String>) {
        var sum = 0
        lines.forEach {
            val groups = it.split(":", "|")
            val winners = groups[1].trim().split(" ").filter { it.isNotEmpty() }
            val ours = groups[2].trim().split(" ").filter { it.isNotEmpty() }
            val pow = ours.filter { it in winners }.size - 1
            if (pow > -1) {
                sum += 2.0.pow(pow).toInt()
            }
        }
        println(sum)
    }

    fun part2(lines: List<String>) {
        var index = 0
        val cards = MutableList(lines.size) { i -> Card(idx = i) }
        lines.forEach {
            val groups = it.split(":", "|")
            val winners = groups[1].trim().split(" ").filter { it.isNotEmpty() }
            val ours = groups[2].trim().split(" ").filter { it.isNotEmpty() }
            val nbrOfWinners = ours.filter { it in winners }.size
            //println("Current: ${cards[index]}")
            val count = cards[index].count
            for (i in index + 1..index + nbrOfWinners) {
                if (i >= cards.size) continue
                cards[i].increment(count)
            }
            //println(cards)
            index++
        }
        println(cards.sumOf { it.count })
    }

    val lines = File("/Users/mange/Downloads/advent23/4").readLines()
    var time = measureTimedValue { part1(lines) }
    println("Part 1 took: ${time.duration.inWholeMilliseconds} ms")
    time = measureTimedValue { part2(lines) }
    println("Part 2 took: ${time.duration.inWholeMilliseconds} ms")
}