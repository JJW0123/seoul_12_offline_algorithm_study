import java.util.PriorityQueue
import kotlin.math.abs

lateinit var visitedIsland : Array<BooleanArray>
lateinit var islands : MutableList<Island>
lateinit var map : Array<IntArray>

fun main() {
    val (n,m) = readln().split(" ").map { it.toInt() }
    map = Array(n) {
        readln().split(" ").map { it.toInt() }.toIntArray()
    }

    val directions = arrayOf(
            intArrayOf(0, 1), intArrayOf(0, -1),
            intArrayOf(1, 0), intArrayOf(-1, 0)
    )
    visitedIsland = Array(n) {
            BooleanArray(m)
    }
    islands = mutableListOf()

    searchAllMap(n,m) { x, y ->
        if (map[x][y] == 1 && !visitedIsland[x][y]) {
            explore(x, y, n, m , directions)
        }
    }

    val size = islands.size
    val islandsGraph = Array(size) { IntArray(size) }

    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            val distance = islands[i].countDistance(islands[j])
            if (distance <= 1) {
                continue
            }
            islandsGraph[i][j] = distance
            islandsGraph[j][i] = distance
        }
    }

    val result = getResultByMinimumDistance(islandsGraph, 0)

    println(result)
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

fun explore(x: Int, y: Int, height: Int, width: Int, directions: Array<IntArray>) {
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
                for (i in standard.second + 1..< another.second) {
                    if (map[standard.first][i] == 1) {
                        return false
                    }
                }
                return true
            }

            for (i in another.second + 1 ..< standard.second) {
                if (map[standard.first][i] == 1) {
                    return false
                }
            }
            return true
        }
    }

}

fun getResultByMinimumDistance(islandsGraph: Array<IntArray>, root: Int): Int {
    val visitedGraph = BooleanArray(islandsGraph.size)
    val queue = PriorityQueue<Pair<Int, Int>>(compareBy { it.second }) // 노드, 거리

    queue.add(Pair(root, 0))

    var totalDistance = 0
    var connectedCount = 0

    while (queue.isNotEmpty()) {
        val (current, dist) = queue.poll()

        if (visitedGraph[current]) continue

        visitedGraph[current] = true
        totalDistance += dist
        connectedCount++

        for (next in islandsGraph.indices) {
            val nextDist = islandsGraph[current][next]
            if (!visitedGraph[next] && nextDist > 0) {
                queue.add(Pair(next, nextDist))
            }
        }
    }

    return if (connectedCount == islandsGraph.size) totalDistance else -1
}
