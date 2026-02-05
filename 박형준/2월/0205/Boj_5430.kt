fun main() {
    val T = readln().toInt()
    repeat(T) {
        val commands = readln()
        val n = readln().toInt()

        var rotate = true;
        val core = readln()
        val list = ArrayDeque<Int>()
        if (n > 0) {
            core.removeSurrounding("[", "]")
                .split(",")
                .map { it.toInt() }
                .forEach { list.add(it) }
        }

        var success = true
        for (ch in commands) {
            if (ch == 'R') {
                rotate = !rotate
            } else if (ch == 'D') {
                val nullCheck = if (rotate) list.removeFirstOrNull() else list.removeLastOrNull()
                if (nullCheck == null) {
                    success = false
                    break
                }
            }
        }
        if (success) {
            val result = if (rotate) list.toList() else list.asReversed()
            println(result.joinToString(separator = ",", prefix = "[", postfix = "]"))
        } else {
            println("error")
        }
    }
}
