package feb._0221

fun main() {
    val tc = readln().toInt()
    val sb = StringBuilder()
    repeat(tc) {
        val (n , m) = readln().split(" ").map { it.toInt() }

        val A = readln().split(" ").map { it.toInt() }
        val B = readln().split(" ").map { it.toInt() }.sorted()

        var result = 0
        for (value in A) {
            result += getLocation(A , B ,value)
        }
        sb.append(result).append("\n")
    }
    print(sb)
}

fun getLocation(a: List<Int>, b: List<Int>, value: Int) : Int {
    var left = 0
    var right = b.size

    while (left < right) {
        val mid = (left + right) / 2
        if (b[mid] >= value) {
            right = mid
        } else {
            left = mid + 1
        }
    }

    return left
}
