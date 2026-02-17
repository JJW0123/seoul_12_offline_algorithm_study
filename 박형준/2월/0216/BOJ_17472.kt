import kotlin.math.abs

lateinit var visitedIsland : Array<BooleanArray>
lateinit var islands : MutableList<Island>
lateinit var map : Array<IntArray>
lateinit var result17472 : IntArray
lateinit var visitedTracking: BooleanArray

fun main() {
    val (n,m) = readln().split(" ").map { it.toInt() }
    result17472 = intArrayOf(Int.MAX_VALUE)
    map = Array(n) {
        readln().split(" ").map { it.toInt() }.toIntArray()
    }

    directions = arrayOf(
            intArrayOf(0, 1), intArrayOf(0, -1),
            intArrayOf(1, 0), intArrayOf(-1, 0)
    )
    visitedIsland = Array(n) {
            BooleanArray(m)
    }
    islands = mutableListOf()

    searchAllMap(n,m) { x, y ->
        if (map[x][y] == 1 && !visitedIsland[x][y]) {
            explore(x, y, n, m)
        }
    }

    visitedTracking = BooleanArray(islands.size)
    for (i in 0 until islands.size) {
        visitedTracking[i] = true
        backtracking(1,islands.size,islands[i], 0)
    }

    println(if (result17472[0] == Int.MAX_VALUE) -1 else result17472[0])
}

fun searchAllMap(
    height : Int,
    width : Int,
    action : (x : Int, y : Int) -> Unit
) {
    for (i in 0 until height) {
        for (j in 0 until width) {
            action(i,j)
        }
    }
}

fun explore(x: Int, y: Int, height: Int, width: Int) {
    val deque = ArrayDeque<Pair<Int, Int>>()

    visitedIsland[x][y] = true
    deque.add(Pair(x, y))
    val island = Island()
    island.addCoordinate(x,y)

    while (deque.isNotEmpty()) {
        val (curX, curY) = deque.removeFirst()
        for ((dx,dy) in directions) {
            if (curX + dx in 0..<height && curY + dy in 0..<width && !visitedIsland[curX + dx][curY + dy] && map[curX + dx][curY + dy] == 1) {
                visitedIsland[curX + dx][curY + dy] = true
                deque.add(Pair(curX + dx, curY + dy))
                island.addCoordinate(curX + dx, curY + dy)
            }
        }
    }

    islands.add(island)
}

class Island(
    val coordinates: MutableList<Pair<Int, Int>> = mutableListOf()
) {
    fun addCoordinate(x: Int, y: Int) {
        coordinates.add(Pair(x, y))
    }
    fun countDistance(other: Island): Int {
        var minDist = Int.MAX_VALUE

        for (standard in coordinates) {
            for (another in other.coordinates) {
                if (standard.first == another.first) {
                    val length = abs(standard.second - another.second) - 1
                    if (length > 1 && isPathClear(standard, another, false)) {
                        minDist = minDist.coerceAtMost(length)
                    }
                }

                if (standard.second == another.second) {
                    val length = abs(standard.first - another.first) - 1
                    if (length > 1 && isPathClear(standard, another, true)) {
                        minDist = minDist.coerceAtMost(length)
                    }
                }
            }
        }

        return if (minDist == Int.MAX_VALUE) -1 else minDist
    }

    private fun isPathClear(standard: Pair<Int, Int>, another: Pair<Int, Int>, vertical: Boolean) : Boolean {
        if (vertical) {
            if (standard.first > another.first) {
                for (i in another.first + 1 ..< standard.first) {
                    if (map[i][standard.second] == 1) {
                        return false
                    }
                }
                return true
            }
            for (i in standard.first + 1 ..< another.first) {
                if (map[i][standard.second] == 1) {
                    return false
                }
            }
            return true
        } else {
            if (standard.second < another.second) {
                for (j in standard.second + 1..< another.second) {
                    if (map[standard.first][j] == 1) {
                        return false
                    }
                }
                return true
            }

            for (i in standard.first + 1 ..< another.first) {
                if (map[i][standard.second] == 1) {
                    return false
                }
            }
            return true
        }
    }
}

fun backtracking(depth: Int, size: Int, before: Island, distance: Int) {
    if (depth == size) {
        result17472[0] = result17472[0].coerceAtMost(distance)
        return
    }

    for (i in 0 until size) {
        if (!visitedTracking[i]) {
            val countDistance = before.countDistance(islands[i])
            if (countDistance == -1) {
                continue
            }
            visitedTracking[i] = true
            backtracking(depth + 1, size, islands[i], distance + countDistance)
            visitedTracking[i] = false
        }
    }
}
