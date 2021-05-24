import org.junit.jupiter.api.Test
import kotlin.collections.Map

class SingleBotTest {
    @Test
    fun test_simple() {
        val map = """
        ............#######......
        ............#######......
        ....####.................
        .............#####.......
        .........................
        ....#####################
        .........................
        .........................
        """.trimIndent()
        val test2 = SingleBotCase(Map(map), Point(24, 0), Point(0, 7), 1.0, emptyList())

        val res = DijkstraWithTimeDimension().findPath(test2)

        test2.printWithPath(res!!)
    }

    @Test
    fun test_obstacles_simple() {
        val map = """
        ............#######......
        ............#######......
        ....####.................
        .............#####.......
        .........................
        ....#####################
        .........................
        .........................
        """.trimIndent()

        val obstacle1 = listOf(TimePoint(23, 0, 0), TimePoint(23, 0, 1), TimePoint(23, 1, 2), TimePoint(23, 2, 3))
        val test2 = SingleBotCase(Map(map), Point(24, 0), Point(0, 7), 0.1, listOf(Obstacle(obstacle1, 0.1)))

        val res = DijkstraWithTimeDimension().findPath(test2)

        println(res)

        test2.printWithPath(res!!)

        //test2.emulate(res)
    }
}