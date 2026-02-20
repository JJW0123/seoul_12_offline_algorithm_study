package feb._0210

fun main() {
    var grid = Array(4) { readln().split(" ").map { it.toInt() }.toIntArray() }
    val dir = readln()[0]

    when (dir) {
        'D' -> down(grid)
        'U' -> up(grid)
        'R' -> right(grid)
        'L' -> left(grid)
    }

    repeat(4) { i->
        println(grid[i].joinToString(separator = " ") { it.toString() })
    }
}

fun left(grid: Array<IntArray>) {
    for (i in 0 until 4) {
        val temp = IntArray(4)
        var idx = 0
        for (j in 0 until 4) {
            if (grid[i][j] > 0) {
                if (idx > 0) {
                    val before = temp[idx - 1]
                    if (before == grid[i][j]) {
                        temp[idx - 1] = before * 2 + 10000
                        continue
                    }
                }
                temp[idx] = grid[i][j]
                idx++
            }
        }
        for (j in 0 until 4) {
            grid[i][j] = temp[j] % 10000
        }
    }
}

fun right(grid: Array<IntArray>) {
    for (i in 0 until 4) {
        val temp = IntArray(4)
        var idx = 0
        for (j in 3 downTo 0) {
            if (grid[i][j] > 0) {
                if (idx > 0) {
                    val before = temp[idx - 1]
                    if (before == grid[i][j]) {
                        temp[idx - 1] = before * 2 + 10000
                        continue
                    }
                }
                temp[idx] = grid[i][j]
                idx++
            }
        }
        for (j in 3 downTo 0) {
            grid[i][j] = temp[3 - j] % 10000
        }
    }
}

fun down(grid: Array<IntArray>) {
    for (j in 0 until 4) {
        val temp = IntArray(4)
        var idx = 0
        for (i in 3 downTo 0) {
            if (grid[i][j] > 0) {
                if (idx > 0) {
                    val before = temp[idx - 1]
                    if (before == grid[i][j]) {
                        temp[idx - 1] = before * 2 + 10000
                        continue
                    }
                }
                temp[idx] = grid[i][j]
                idx++
            }
        }
        for (i in 3 downTo 0) {
            grid[i][j] = temp[3 - i] % 10000
        }
    }
}

fun up(grid: Array<IntArray>) {
    for (j in 0 until 4) {
        val temp = IntArray(4)
        var idx = 0
        for (i in 0 until 4) {
            if (grid[i][j] > 0) {
                if (idx > 0) {
                    val before = temp[idx - 1]
                    if (before == grid[i][j]) {
                        temp[idx - 1] = before * 2 + 10000
                        continue
                    }
                }
                temp[idx] = grid[i][j]
                idx++
            }
        }
        for (i in 0 until 4) {
            grid[i][j] = temp[i] % 10000
        }
    }
}
