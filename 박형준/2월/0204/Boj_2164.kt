import kotlin.math.pow

fun main() {
    val n = readln().toInt()
    if (n == 1) {
        println(1)
        return
    }

    var k = 0
    while (n > 2.toDouble().pow(k)) {
        k++
    }
    val remain = n - 2.toDouble().pow(k - 1).toInt()
    println(2 * remain)
}
