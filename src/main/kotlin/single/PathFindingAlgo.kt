package single

import TimePoint

interface PathFindingAlgo {
    fun findPath(case: SingleBotCase): List<TimePoint>?
}