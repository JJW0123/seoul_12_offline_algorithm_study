package feb._0224

val directions = arrayOf(intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(0, -1))
lateinit var map : List<List<Int>>
fun main() {
    val (n,m) = readln().split(" ").map { it.toInt() }

    map = List(n) { readln().split(" ").map { it.toInt() } }

    val dp = Array(n) { IntArray(m) { -1 } }

    val result = dfs(0,0,dp,n,m)

    println(result)
}

fun dfs(x: Int, y: Int, dp: Array<IntArray>, n: Int, m: Int): Int {

    if (dp[x][y] > -1) {
        return dp[x][y]
    }

    if (x == n - 1 && y == m - 1) {
        return 1
    }

    dp[x][y] = 0

    for ((dx, dy) in directions) {
        if (x + dx in 0..<n && y + dy in 0..<m && map[x][y] > map[x + dx][y + dy]) {
            dp[x][y] += dfs(x + dx, y + dy, dp, n, m)
        }
    }

    return dp[x][y]
}
