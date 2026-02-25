package feb._0223

import java.util.TreeSet
import kotlin.math.min

fun main() {
    val (n, q) = readln().split(" ").map { it.toInt() }

    val sb = StringBuilder()
    val map = readln().split(" ").map { it.toInt() }.toMutableList()
    val tree = TreeSet<Int>()

    for (i in map.indices) {
        if (map[i] == 1) {
            tree.add(i)
        }
    }

    var location = 0

    repeat(q) {
        val commands = readln().split(" ")
        when (commands[0]) {
            "1" -> {
                val loc = commands[1].toInt() - 1
                if (map[loc] == 1) {
                    map[loc] = 0
                    tree.remove(loc)
                } else {
                    map[loc] = 1
                    tree.add(loc)
                }
            }
            "2" -> location = (location + commands[1].toInt()) % n
            "3" -> {
                if (tree.isEmpty()) {
                    sb.append(-1)
                } else if (map[location] == 1) {
                    sb.append(0)
                } else {
                    val higher = tree.higher(location) ?: (n + location)
                    val lower = tree.first() ?: location

                    val value = min(higher - location, n - (location - lower))
                    sb.append(value)
                }
                sb.append("\n")
            }
        }
    }
    print(sb)
}
