import java.lang.IllegalStateException

data class SingleBotCase(
    val map: Map,
    val startPoint: Point,
    val endPoint: Point,
    val rad: Double,
    val obstacles: List<Obstacle>
) {
    fun isCorrect(point: TimePoint): Boolean {
        if (!map.isFree(point.getPoint())) {
            return false
        }

        obstacles.forEach { obs ->
            for (i in obs.points.indices) {
                val t = obs.points[i]
                if (t.time == point.time && point.getPoint().dist2(t.getPoint()) <= (rad + obs.R) * (rad + obs.R)) {
                    return@isCorrect false
                }
            }
        }

        return true
    }

    fun printWithPath(path: List<TimePoint>) {
        val resLines = getMapLines()

        path.forEach {
            if (resLines[it.y][it.x] == 1) {
                println("Wrong path. The path goes through obstacle")
                resLines[it.y][it.x] = 3
            } else {
                resLines[it.y][it.x] = 2
            }
        }
        println("Full time: ${path.last().time}")
        println(getStrFromLines(resLines))
    }

    fun emulate(path: List<TimePoint>) {
        path.forEach { pathPoint ->
            val resLines = getMapLines()
            println("Time: ${pathPoint.time}")
            resLines[pathPoint.y][pathPoint.x] = if (map.isFree(pathPoint.getPoint())) 2 else 3
            obstacles.forEachIndexed {io, obs ->
                obs.points.forEach { op ->
                    if (op.time == pathPoint.time) {
                        resLines[op.y][op.x] =
                            if (op.x == op.y) {
                                3
                            } else {
                                io + 4
                            }
                    }
                }
            }
            println(getStrFromLines(resLines))
            println()
        }
    }

    private fun getStrFromLines(lines: Array<Array<Int>>): String {
        val res = lines.joinToString("") { it.joinToString("") { intIt ->
            when (intIt) {
                0 -> "."
                1 -> "#"
                2 -> "*"
                3 -> "!"
                else -> (intIt - 3).toString()
            }
        } + "\n" }
        return res
    }

    private fun getMapLines(): Array<Array<Int>> {
        val mapLines = map.getGrid()

        val resLines = Array(map.h) { Array(map.w) { 0 } }
        for (i in 0 until map.h) {
            for (j in 0 until map.w) {
                resLines[i][j] = if (mapLines[i][j]) 0 else 1
            }
        }
        return resLines
    }
}