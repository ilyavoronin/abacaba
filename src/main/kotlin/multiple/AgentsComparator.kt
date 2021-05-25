package multiple

import Point

abstract class AgentsComparator private constructor() {

    abstract fun compare(agent1: Pair<Point, Point>, agent2: Pair<Point, Point>, case: MultiAgentCase): Int

    companion object {
        val FASTEST_FIRST = object : AgentsComparator() {
            override fun compare(agent1: Pair<Point, Point>, agent2: Pair<Point, Point>, case: MultiAgentCase): Int {
                return agent1.first.dist2(agent2.first).compareTo(agent2.first.dist2(agent2.second))
            }
        }

        val LONGEST_FIRST = object : AgentsComparator() {
            override fun compare(agent1: Pair<Point, Point>, agent2: Pair<Point, Point>, case: MultiAgentCase): Int {
                return -agent1.first.dist2(agent2.first).compareTo(agent2.first.dist2(agent2.second))
            }
        }
    }
}