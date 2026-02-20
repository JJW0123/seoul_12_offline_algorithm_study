package feb._0219

fun main() {
    val tc = readln().toInt()
    repeat(tc) {
        val n = readln().toInt()
        val list = mutableListOf<String>()

        repeat(n) { list.add(readln()) }
        list.sortWith(compareBy<String> { it }.thenBy { it.length })

        var result : String? = null
        for (i in 0 until n - 1) {
            val prefix = list[i + 1].startsWith(list[i])
            if (prefix) {
                result = "NO"
                break
            }
        }

        println(result ?: "YES")
    }
}