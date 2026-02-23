package feb._0220

import kotlin.math.abs

fun main() {
    val (n, m) = readln().split(" ").map { it.toInt() }
    val locations = readln().split(" ").map { it.toInt() }

    val positives = mutableListOf<Int>()
    val negatives = mutableListOf<Int>()
    var maxDist = 0

    for (loc in locations) {
        if (loc > 0) positives.add(loc)
        else negatives.add(loc)

        if (abs(loc) > maxDist) {
            maxDist = abs(loc)
        }
    }

    positives.sortDescending()
    negatives.sort()

    var total = 0
    for (i in 0 until positives.size step m) {
        total += positives[i] * 2
    }

    for (i in 0 until negatives.size step m) {
        total -= negatives[i] * 2
    }

    println(total - maxDist)
}
