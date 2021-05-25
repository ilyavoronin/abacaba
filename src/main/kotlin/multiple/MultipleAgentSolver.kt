package multiple

import Obstacle
import TimePoint
import single.PathFindingAlgo
import single.SingleBotCase

class MultipleAgentSolver(
    private val pathFindingAlgo: PathFindingAlgo,
    private val agentsComparator: AgentsComparator
) {
    fun solve(case: MultiAgentCase): List<List<TimePoint>>? {
        val agents = case.startFinishPoints.sortedWith { a, b -> agentsComparator.compare(a, b, case) }

        val currObstacles = case.obstacles.toMutableList()
        val res = mutableListOf<List<TimePoint>>()

        agents.forEach {
            val newRes = pathFindingAlgo.findPath(SingleBotCase(case.map, it.first, it.second, currObstacles)) ?: return null
            res.add(newRes)
            currObstacles.add(Obstacle(newRes, 0.5))
        }

        return res
    }
}