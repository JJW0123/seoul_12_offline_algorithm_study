fun main() {
    val n = readln().toInt()
    val queue = ArrayDeque<Int>()
    val sb = StringBuilder()

    repeat(n) {
        val command = readln().split(" ")
        val result = when (command[0]) {
            "push" -> queue.add(command[1].toInt())
            "pop" -> queue.removeFirstOrNull() ?: -1
            "size" -> queue.size
            "empty" -> queue.isEmpty().toInt()
            "front" -> queue.getOrNull(0) ?: -1
            "back" -> queue.getOrNull(queue.size - 1) ?: -1
            else -> 0
        }

        if (result != true) sb.append(result).append("\n")
    }
    print(sb)
}

fun Boolean.toInt() = if (this) 1 else 0