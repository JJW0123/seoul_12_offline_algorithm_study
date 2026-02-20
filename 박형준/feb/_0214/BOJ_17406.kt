package feb._0214

import kotlin.math.min

val dir17406 = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))
lateinit var visited : BooleanArray
lateinit var order : Array<IntArray>
var result17406 = Int.MAX_VALUE

fun main() {
    val (n, m, k) = readln().split(" ").map { it.toInt() }
    val array = Array(n) {
        readln().split(" ").map { it.toInt() }.toIntArray()
    }

    visited = BooleanArray(k)
    order = Array(k) { IntArray(3) }
    val commands = Array(k) {
        readln().split(" ").map { it.toInt() }.toIntArray()
    }
    backtracking(commands,array, k, 0)

    println(result17406)
}

fun backtracking(commands: Array<IntArray>, arrayR: Array<IntArray>, k: Int, depth: Int) {
    if (depth == k) {
        val array = RotateArray(arrayR)
        for ((r,c,s) in order) {
            array.rotate(r - s - 1, c - s - 1, r + s - 1, c + s - 1, s)
        }
        result17406 = min(result17406, array.getMinValue())
        return
    }

    for (i in 0 until k) {
        if (!visited[i]) {
            visited[i] = true
            order[depth] = commands[i]
            backtracking(commands, arrayR, k, depth + 1)
            visited[i] = false
        }
    }
}

class RotateArray(
    origin: Array<IntArray>
) {
    val array = Array(origin.size) { origin[it].clone() }

    fun rotate(
        startX: Int, startY: Int, endX: Int, endY: Int, count : Int,
    ) {
        var x = startX
        var y = startY
        var beforeValue = array[x][y]

        for (idx in 0 until 4) {
            val (dx,dy) = dir17406[idx]

            while (x + dx in startX .. endX && y + dy in startY .. endY) {
                val temp = array[x+dx][y+dy]
                array[x+dx][y+dy] = beforeValue
                beforeValue = temp
                x += dx
                y += dy
            }
        }

        if (count == 1) {
            return
        }

        rotate(startX+1, startY+1, endX-1, endY-1, count - 1)
    }

    fun getMinValue(): Int {
        var minValue = Int.MAX_VALUE
        for (line in array) {
            minValue = min(minValue, line.sum())
        }
        return minValue
    }
}