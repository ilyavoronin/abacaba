import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class DijkstraWithTimeDimension: PathFindingAlgo {
    override fun findPath(case: SingleBotCase): List<TimePoint>? {
        val open = TreeSet<TimePoint> { a, b ->
            if (a.time == b.time) {
                if (a.x == b.x) {
                    a.y.compareTo(b.y)
                } else {
                    a.x.compareTo(b.x)
                }
            } else {
                a.time.compareTo(b.time)
            }
        }
        open.add(TimePoint(case.startPoint, 0))

        val usedPoints = HashSet<TimePoint>()
        usedPoints.add(TimePoint(case.startPoint, 0))

        val prevPoints = HashMap<TimePoint, TimePoint>()

        var finalPoint: TimePoint? = null

        while (open.isNotEmpty()) {
            val currPoint = open.pollFirst()!!

            if (currPoint.x == case.endPoint.x && currPoint.y == case.endPoint.y) {
                finalPoint = currPoint
                break
            }

            val neigs = getNeighbours(currPoint, case)
            neigs.forEach {
                if (!usedPoints.contains(it)) {
                    usedPoints.add(it)
                    open.add(it)

                    prevPoints[it] = currPoint
                }
            }
        }
        if (finalPoint == null) {
            return null
        }

        return restorePath(finalPoint, prevPoints)
    }

    private fun getNeighbours(point: TimePoint, case: SingleBotCase): List<TimePoint> {
        return point.getNeighbours().filter { case.isCorrectTransition(point, it) }
    }

    private fun restorePath(point: TimePoint, prevs: HashMap<TimePoint, TimePoint>): List<TimePoint> {
        fun restorePathInt(point: TimePoint, prevs: HashMap<TimePoint, TimePoint>): List<TimePoint> {
            return if (prevs.containsKey(point)) {
                val res = restorePathInt(prevs[point]!!, prevs).toMutableList();
                res.add(point)
                res
            } else {
                listOf(point)
            }
        }

        return restorePathInt(point, prevs)
    }
}