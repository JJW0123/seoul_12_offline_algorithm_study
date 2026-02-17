lateinit var direction : Array<Array<IntArray>>

fun main() {
    val n = readln().toInt()
    val map = Array(n) {
        readln().split(" ").map { it.toInt() }.toIntArray()
    }

    direction = arrayOf(
        arrayOf(intArrayOf(0, 1), intArrayOf(1, 1)),
        arrayOf(intArrayOf(1, 0), intArrayOf(1, 1)),
        arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(1, 1))
    )

    val stack = ArrayDeque<IntArray>()
    stack.add(intArrayOf(0, 1, 0))

    var result = 0;
    while (stack.isNotEmpty()) {
        val (x, y, pipeStatus) = stack.removeLast()
        if (Pair(x, y) == Pair(n - 1, n - 1)) {
            result++
            continue
        }

        val directions = direction[pipeStatus]
        for ((dx, dy) in directions) {
            if (x + dx in 0..<n && y + dy in 0..<n && map[x + dx][y + dy] == 0) {
                val status = getPipeStatus(dx, dy)
                if (status == 2 && (map[x + 1][y] != 0 || map[x][y+1] != 0)) {
                    continue
                }
                stack.add(intArrayOf(x + dx, y + dy, status))
            }
        }
    }
    println(result)
}

fun getPipeStatus(dx: Int, dy: Int) : Int {
    return when (Pair(dx, dy)) {
        Pair(0,1) -> 0
        Pair(1,0) -> 1
        else -> 2
    }
}
