import java.util.Collections

fun main() {

    class MyComparable(val list: List<Int>) : Comparable<List<Int>> {
        override fun compareTo(other: List<Int>): Int {
            if (list == other) return 0
            return if (list.indices.any { index -> list[index] > (other[index]) }) {
                1
            } else {
                -1
            }
        }

    }

    fun <T> Iterable<T>.sortedWith(comparator: Comparator<in T>):
            List<T> {
        if (this is Collection) {
            if (size <= 1) return this.toList()
            @Suppress("UNCHECKED_CAST")
            return (toTypedArray<Any?>() as Array<T>).apply {
                sortWith(comparator)
            }.asList()
        }
        return toMutableList().apply { sortWith(comparator) }
    }

    class MyComparator : Comparator<List<Int>> {
        override fun compare(o1: List<Int>, o2: List<Int>): Int {
            return MyComparable(o1).compareTo(o2)
        }

    }


    val cards = mapOf(
        "T" to 10,
        "J" to 11,
        "Q" to 12,
        "K" to 13,
        "A" to 14,
    )

    fun String.cardToInt(): Int {
        return when {
            cards.keys.contains(this) -> {
                val key = cards.keys.first { it == this }
                cards[key]!!
            }

            else -> this.toInt()
        }
    }

    class Hand(val list: List<Int>, val bid: Int) {
        override fun toString(): String {
            return "${list.sorted()} $list bid: $bid"
        }
        /*
               fun type() : Type {
                   val biggestSet = list.groupBy { it }.values.maxOfOrNull { it.size }

               }


                println(map.values.map { it.size }.max()) // största setet
                               val biggestSet = map.values.map { it.size }.max()
                               println(map.values.filter { it.size == biggestSet }) // dubbel par om denna ger två så är tank dubbel par
                */
    }

    fun test() {
        /*val listToSort = listOf(
            listOf(1000, 4000),
            listOf(300, 200),
            listOf(300, 200),
            listOf(300, 200),
            listOf(200, 1),
            listOf(1, 1),
            listOf(4, 4),
            listOf(1, 200),
            listOf(500, 400)
        )
        println(listToSort.sortedWith(MyComparator()))

        System.exit(0)*/

        val lines = readInput("Day7_test")
        //  val input = "QQQJA"
        val hands = emptyList<Hand>().toMutableList()
        lines.forEach {
            val input = it.split(" ")[0]
            println(input.split("").filterNot { it.isEmpty() }.map { it.cardToInt() })
            // val numbers = listOf(1, 1, 2, 2, 3, 4, 5)
            val map = input.split("").filterNot { it.isEmpty() }.map { it.cardToInt() }.groupBy { it }
            hands.add(Hand(list = input.split("").filterNot { it.isEmpty() }.map { it.cardToInt() }, bid=it.split(" ")[1].toInt()))

            /*
                        println(map.keys.max()) // högsta kortet
                        println(map.values.map { it.size }) // storleken på seten
                        println(map.values.map { it.size }.max()) // största setet
                        val biggestSet = map.values.map { it.size }.max()
                        println(map.values.filter { it.size == biggestSet }) // dubbel par om denna ger två så är tank dubbel par
                    */
        }
        println(hands)
    }

    test()

    val lines = readInput("Day7_test")
    //part1(lines)
    //part2(lines)
}


sealed class Type {
    data object Single : Type()
    data object Pair : Type()
    data object TwoPair : Type()
    data object Triple : Type()
    data object Four : Type()
    data object FullHouse : Type()
    data object Five : Type()
}


val groupSizes = setOf(2, 3, 4, 5)
/*
fun Set<Int>.toRank() : Rank = when(this.groupBy { it }) {
    3 -> Rank.Pair

}*/

// ide "divide and conquer",
// om man gör group by på värdena så får man snabbt reda på om handen innehöll par eller mer
// ju färre grupper ju högre valör
// fyrtal slår kåk (exempel på grupp med bara 2 olika valör set)
// tretal har set med 3 värden
// två par har set med 3 värden
// ett par har set med 4 värden