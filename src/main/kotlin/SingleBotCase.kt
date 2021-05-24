import java.io.File
import java.lang.IllegalStateException

data class SingleBotCase(
    val map: Map,
    val startPoint: Point,
    val endPoint: Point,
    val obstacles: List<Obstacle>
) {
    companion object {
        fun fromFile(mapFilePath: String, obsFilePath: String, startPoint: Point, endPoint: Point): SingleBotCase {
            val obstacles = File(obsFilePath).readLines().chunked(3).map { obsRepr ->
                val xs = obsRepr[1].split(" ").map {it.toInt()}
                val ys = obsRepr[2].split(" ").map { it.toInt() }
                Obstacle(xs.zip(ys).mapIndexed {i, p -> TimePoint(Point(p.second, p.first), i.toLong()) }, 0.5)
            }
            return SingleBotCase(
                Map.fromFile(mapFilePath),
                startPoint,
                endPoint,
                obstacles
            )
        }
    }

    fun isCorrectTransition(pointFrom: TimePoint, pointTo: TimePoint): Boolean {
        if (!map.isFree(pointTo.getPoint())) {
            return false
        }
        obstacles.forEach { obs ->
            val obsPoint1 = obs.points.getOrElse(pointFrom.time.toInt(), {obs.points.last()})
            val obsPoint2 = obs.points.getOrElse(pointTo.time.toInt(), {obs.points.last()})

            if (pointTo.getPoint() == obsPoint2.getPoint() || obsPoint2.getPoint() == pointFrom.getPoint() && obsPoint1.getPoint() == pointTo.getPoint()) {
                return@isCorrectTransition false
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
            println("Time: ${pathPoint.time}")
            println(getMapAtPathPoint(pathPoint))
            println()
        }
    }

    private fun getMapAtPathPoint(pathPoint: TimePoint): String {
        val resLines = getMapLines()

        resLines[pathPoint.y][pathPoint.x] = if (map.isFree(pathPoint.getPoint())) 2 else 3
        obstacles.forEachIndexed {io, obs ->
            obs.points.forEachIndexed { jo, op ->
                if (op.time == pathPoint.time || op.time < pathPoint.time && jo == obs.points.lastIndex) {
                    resLines[op.y][op.x] =
                        if (op.x == op.y) {
                            3
                        } else {
                            io + 4
                        }
                }
            }
        }
        return getStrFromLines(resLines)
    }

    fun emulateToFile(path: List<TimePoint>, filePath: String) {
        val f = File(filePath)
        f.writeText("")
        path.forEach { point ->
            f.appendText(getMapAtPathPoint(point))
            f.appendText("\n\n\n");
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