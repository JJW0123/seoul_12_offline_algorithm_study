package feb._0211

import kotlin.math.max

fun main() {
    val (n,k) = readln().split(" ").map { it.toInt() }

    val dp = IntArray(k + 1)
    repeat(n) {
        val (w , v) = readln().split(" ").map { it.toInt() }
        for (i in k downTo w) {
            dp[i] = max(dp[i], dp[i - w] + v)
        }
    }
    println(dp[k])
}