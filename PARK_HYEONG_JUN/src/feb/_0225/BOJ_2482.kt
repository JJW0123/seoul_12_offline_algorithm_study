package feb._0225

fun main() {
    val n = readln().toInt()
    val k = readln().toInt()

    val dp = Array(n+1) { IntArray(k+1) { 0 } }

    if (k == 1) {
        println(n)
        return
    }

    if (n < 2 * k) {
        println(0)
        return
    }

    for (i in 1..n) {
        dp[i][0] = 1
        dp[i][1] = i
    }

    for (i in 3..n) {
        for (j in 2..k) {
            dp[i][j] = (dp[i-2][j-1] + dp[i-1][j]) % 1_000_000_003
        }
    }
    dp[n][k] = dp[n-3][k-1] + dp[n-1][k]

    println(dp[n][k] % 1_000_000_003)
}