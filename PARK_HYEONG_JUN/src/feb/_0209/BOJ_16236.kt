package feb._0209

var result = 0

fun main() {
    val n = readln().toInt()
    var locationX = -1
    var locationY = -1

    val array = Array(n) { x ->
        readln().split(" ").mapIndexed { y, v ->
            var value = v.toInt()
            if (value == 9) {
                locationX = x
                locationY = y
                value = 0
            }
            value
        }.toIntArray()
    }
    val directions = arrayOf(intArrayOf(-1, 0), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(0, 1))

    var size = 2
    var count = 0

    val coordinate = ArrayDeque<IntArray>()
    coordinate.add(intArrayOf(locationX, locationY, 0))

    while (true) {
        val target = bfs(locationX, locationY, size, n, array , directions)

        if (target.isEmpty()) {
            break
        }

        val (x, y, dist) = target
        result += dist

        locationX = x
        locationY = y
        array[x][y] = 0

        count++
        if (count == size) {
            count = 0
            size++
        }
    }

    println(result)
}

fun bfs(startX: Int, startY: Int, size: Int, n: Int, array: Array<IntArray>, directions: Array<IntArray>): IntArray {
    val visited = Array(n) { BooleanArray(n) }
    val q = ArrayDeque<IntArray>()
    q.add(intArrayOf(startX, startY, 0))
    visited[startX][startY] = true

    val candidates = mutableListOf<IntArray>()
    var minDist = -1

    while (q.isNotEmpty()) {
        val (x, y, d) = q.removeFirst()

        if (minDist != -1 && d > minDist) {
            break
        }

        if (array[x][y] in 1 until size) {
            candidates.add(intArrayOf(x, y, d))
            minDist = d
        }

        for (dir in directions) {
            val nx = x + dir[0]
            val ny = y + dir[1]

            if (nx in 0 until n && ny in 0 until n &&
                !visited[nx][ny] && array[nx][ny] <= size
            ) {
                visited[nx][ny] = true
                q.add(intArrayOf(nx, ny, d + 1))
            }
        }
    }

    candidates.sortWith(
        compareBy<IntArray> { it[2] }
            .thenBy { it[0] }
            .thenBy { it[1] }
    )

    return if (candidates.isEmpty()) intArrayOf() else candidates.first()
}
