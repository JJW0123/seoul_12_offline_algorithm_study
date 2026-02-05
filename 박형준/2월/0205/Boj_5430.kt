fun main() {
    val T = readln().toInt()
    for (tc in 1..T) {
        val commands = readln().toCharArray()
        val n = readln().toInt()

        var rotate = true;
        val core = readln().removeSurrounding("[", "]")
        var list = ArrayDeque<Int>()
        if (core.isNotEmpty()) {
            val toList = core.split(",").map { it.toInt() }
                .toList()

            list = ArrayDeque(toList)
        }

        var success = true
        for (ch in commands) {
            if (ch == 'R') {
                rotate = !rotate
            } else if (ch == 'D') {
                try {
                    if (rotate) {
                        list.removeFirst()
                    } else {
                        list.removeLast()
                    }
                } catch (e: RuntimeException) {
                    success = false
                    break
                }
            }
        }
        if (success) {
            val result = if (rotate) list.toList() else list.reversed()
            println(result.joinToString(separator = ",", prefix = "[", postfix = "]"))
        } else {
            println("error")
        }
    }
}
