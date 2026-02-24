package feb._0222

import java.util.TreeSet

fun main() {
    val n = readln().toInt()
    val sb = StringBuilder()
    val tree = TreeSet(
        compareByDescending<Pair<Int, Int>> { it.second }
            .thenByDescending { it.first }
    )
    val map = HashMap<Int, Int>()

    repeat(n) {
        val (p, l) = readln().split(" ").map { it.toInt() }
        map[p] = l
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
            "add" -> {
                val p = commandline[1].toInt()
                val l = commandline[2].toInt()
                tree.add(Pair(p, l))
                map[p] = l
            }
            "solved" -> {
                val p = commandline[1].toInt()
                tree.remove(Pair(p, map.remove(p)))
            }
        }
    }
    print(sb)
}
