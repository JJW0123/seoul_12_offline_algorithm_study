package feb._0205

import java.util.Scanner

fun main() {
    val reader = Scanner(System.`in`)
    val n = reader.nextInt()
    val m = reader.nextInt()

    val deque = ArrayDeque<Int>()

    for (i in 1..n) {
        deque.add(i)
    }

    var result = 0
    for (i in 0 until m) {
        val num = reader.nextInt()
        var count = 0

        while (deque.isNotEmpty()) {
            val value = deque.removeFirst()
            if (value == num) break

            count++
            deque.add(value)
        }

        if (count > deque.size + 1 - count) {
            count = deque.size + 1 - count
        }

        result += count
    }

    println(result)
}