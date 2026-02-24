package feb._0222

import java.util.TreeSet

fun main() {
    val n = readln().toInt()
    val sb = StringBuilder()
    val tree = TreeSet(
        compareByDescending<Pair<Int, Int>> { it.second }
            .thenByDescending { it.first }
    )

    repeat(n) {
        val (p, l) = readln().split(" ").map { it.toInt() }
        tree.add(Pair(p, l))
    }

    val m = readln().toInt()

    repeat(m) {
        val commandline = readln().split(" ")
        when (commandline[0]) {
            "recommend" -> if (commandline[1] == "1") {
                sb.append(tree.first().first).append("\n")
            } else {
                sb.append(tree.last().first).append("\n")
            }
            "add" -> tree.add(Pair(commandline[1].toInt(), commandline[2].toInt()))
            "solved" -> tree.removeIf { it.first == commandline[1].toInt() }
        }
    }
    print(sb)
}
