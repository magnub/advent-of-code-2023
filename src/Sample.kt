package twentytwo

import DefaultHacker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.milliseconds

const val threshold: Long = 50


class Sample(override val coroutineContext: CoroutineContext) : CoroutineScope {

    private suspend fun changer(time: Long): Boolean {
        return when {
            System.currentTimeMillis() - time > threshold -> true
            else -> false
        }
    }

    enum class Numbers {
        ONE, TWO
    }

    private fun f1(now: Long) = flow {
        emit(changer(now))
        delay(threshold)
        emit(changer(now))
    }

    fun f11() = flow {
        emit(true)
        delay(1000)
        emit(false)
    }.filter { true }

    fun f2() = flow {
        //  for(i in 1..5) {
        //      emit(i)
        //      delay(200)
        // }
        emitAll(f11())
    }.onStart { delay(2000) }

    fun f31() = flow {
        for (i in 1..5) {
            emit(i)
            delay(200)
        }
    }


    fun f3() = combine(f1(System.currentTimeMillis()), f2()) { number, truth ->
        println(number.toString() + truth)
        //when {
        //   truth == true -> println("truth")
        //  else -> println(number)
        //}
    }

    fun f4() = flow {
        emit(1)
        delay(5000)
        emit(2)
    }

    fun floww() = flow {
        emit(1)
        delay(90)
        emit(2)
        delay(90)
        emit(3)
        delay(1010)
        emit(4)
        delay(1010)
        emit(5)
    }.debounce(3000).distinctUntilChanged()

    fun flowww() = flow {
        emit("hej1")
        delay(90)
        emit("hej2")
        delay(90)
        emit("hej3")
        delay(1010)
        emit("hej4")
        delay(1010)
        emit("hej5")
    }.debounce(200).distinctUntilChanged()

    fun flowc() = combine(floww(), flowww()) { int1, int2 ->
        println("$int1$int2")
    }

    fun flowm() = merge(floww(), flowww())

    fun f22() = flow {
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(2)
    }.stateIn(this, SharingStarted.Lazily, 0).debounce(2000)

    fun f23() = flow {
        emit(1)
        delay(3000)
        emit(2)
        // flow(3)
    }

    fun f24() = emptyFlow<Int>()


    fun ffff() = flow {
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(3)
        delay(1000)
        emit(4)
    }.timeout(100.milliseconds).catch { exception ->
        if (exception is TimeoutCancellationException) {
            // Catch the TimeoutCancellationException emitted above.
            // Emit desired item on timeout.
            emit(-1)
        } else {
            // Throw other exceptions.
            throw exception
        }
    }.onEach {
        delay(300) // This will not cause a timeout
    }

    fun f27() = flow {
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(2)
    }

    fun f123() = flow {
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(2)
    }

    fun f1234() = flow {
        emit(true)
        delay(200)
        emit(false)
    }

    fun wha() = flow {
        delay(5000)
        emit("hej")
    }
}

fun combineTest() = runBlocking {
    val sample = Sample(coroutineContext)
    combine(sample.f123(), sample.f1234()) { f1, _ ->
        f1
    }.collect { println(it) }
    sample.f22().collect { println(it) }
}

fun stringFlow1() = emptyFlow<String>()
//fun stringFlow1() = flowOf("aha")
fun stringFlow2() = flowOf("haha")

data class StringHolder(
    val first : String,
    val second : String
)

fun ct() : Flow<StringHolder> = combine(stringFlow1(), stringFlow2()) {
            e1, e2 -> StringHolder(e1, e2)
    }.filterNotNull()


fun main() {
    /*val set1 = setOf(1,2,3)
    val set2 = setOf(2,3,4)
    println(set1-set2)
    println(set2-set1)*/

    println("testar lite ha sa kul detta blev".toHacker())

}

fun String.toHacker() = DefaultHacker(mapping).toHacker(this)

val mapping: (Char) -> Char = { char ->
    when (char) {
        'a' -> '4'
        'b' -> '8'
        'e' -> '3'
        'i' -> '1'
        'g' -> '6'
        'o' -> '0'
        's' -> '5'
        't' -> '7'
        else -> char
    }
}

/*fun main() = runBlocking{
    //ct().onEmpty { print("hej") }.collect { print(it)}
    print(ct().firstOrNull() != null)
    //val sample = Sample(coroutineContext)
    //println(  sample.wha().onStart { print("start") }.collect {print(" $it")} )

    //combineTest()
    /* val reg =
         "^[a-zA-Z0-9+%/=\$!'._-]{0,64}@[a-zA-Z0-9][a-zA-Z0-9-]{1,256}(\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+$".toRegex()
     val regex = "[/!$\\d+]{0,64}".toRegex()
     println(reg.matches("1$23@email.com"))
     println(reg.matches("test@email.com"))
     println(reg.matches("123@email.com"))
     println(reg.matches("/%3$@email.com"))*/
}*/

@FlowPreview
fun testF22() = runBlocking {
    val sample = Sample(coroutineContext)
    sample.f22().collect { println(it) }
}

@FlowPreview
fun testMerge() = runBlocking {
    val sample = Sample(coroutineContext)
    sample.flowm().collect { println(it) }
}


@FlowPreview
fun testDeb() = runBlocking {
    val sample = Sample(coroutineContext)
    sample.floww().collect { println(it) }
}

@FlowPreview
fun tr() = runBlocking {
    val sample = Sample(coroutineContext)
    //  sample.f11().collect { println(it) }
    // println(".....")
    combine(sample.f2().filter { it == true }, sample.f31()) { f1, f2 ->
        println("$f1 $f2")
    }.collect()
}

@FlowPreview
fun dif() = runBlocking {
    val flow1 = flow {
        repeat(10) {
            emit(it)
            delay(1000) // Emits a value every second
        }
    }

    val flow2 = flowOf("A", "B", "C")

    val combinedFlow = flow1.combine(flow2) { value1, value2 ->
        "$value1 - $value2"
    }

    combinedFlow.collect {
        println(it)
    }
}

@FlowPreview
fun trams() = runBlocking {
    //val sample = Sample(coroutineContext)

    flow {
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(1)
        delay(200)
        emit(2)
    }.stateIn(this, SharingStarted.Eagerly, 1).collectLatest { println(it) }


    //sample.f27().stateIn(this, SharingStarted.Eagerly, 0).collect { println(it) }

    // delay(4000)
    // println(sample.f22().last())
    //try {
    //    println(sample.f24().last())
    //} catch (e:NoSuchElementException) {

    //}


    //sample.f22().distinctUntilChanged().collect { value -> println(value) }

    //println(sample.f22().first())


    //sample.floww().collect{value -> println(value) }
    //sample.f3().take(1).collect()
    /*
        val list = mutableListOf<Int>()
        sample.f4().flatMapLatest { integer ->
            flow {
                list.add(integer)
                emit(integer)
            }
        }.map { list }.collect()

        println(list)*/

    // println("---")

    // sample.f4().stateIn(this, SharingStarted.Eagerly, 1).map { println("hej") }.collect()

    /* sample.foo().join()
     println("---------")
     sample.fooIo().join()
     println("---------")
     sample.fooWithContext().join()
     println("---------")
     sample.fooWithFlowOn().join()
     println("---------")
     sample.fooWithFlowOnOuter().join()
     println("---------")*/
}
