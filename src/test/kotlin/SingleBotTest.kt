import org.junit.jupiter.api.Test
import kotlin.collections.Map

class SingleBotTest {
    @Test
    fun test_simple_dij() {
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
        val test2 = SingleBotCase(Map(map), Point(24, 0), Point(0, 7), emptyList())

        val res = DijkstraWithTimeDimension().findPath(test2)

        test2.printWithPath(res!!)
    }

    @Test
    fun test_obstacles_simple_dij() {
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

        val obstacle1 = listOf(TimePoint(23, 0, 0), TimePoint(23, 0, 1), TimePoint(23, 1, 2), TimePoint(23, 2, 3), TimePoint(23, 3, 4), TimePoint(23, 4, 5))
        val obstacle2 = listOf(TimePoint(21, 0, 0), TimePoint(22, 0, 1), TimePoint(23, 0, 2), TimePoint(23, 1, 3))
        val test2 = SingleBotCase(Map(map), Point(24, 0), Point(0, 7), listOf(Obstacle(obstacle1, 0.1), Obstacle(obstacle2, 0.2)))

        val res = DijkstraWithTimeDimension().findPath(test2)

        test2.printWithPath(res!!)
    }

    @Test
    fun test_simple_sipp() {
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
        val test2 = SingleBotCase(Map(map), Point(24, 0), Point(0, 7), emptyList())

        val res = SIPP().findPath(test2)

        test2.printWithPath(res!!)
    }

    @Test
    fun test_obstacles_simple_sipp() {
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

        val obstacle1 = listOf(TimePoint(23, 0, 0), TimePoint(23, 0, 1), TimePoint(23, 1, 2), TimePoint(23, 2, 3), TimePoint(23, 3, 4), TimePoint(23, 4, 5))
        val obstacle2 = listOf(TimePoint(21, 0, 0), TimePoint(22, 0, 1), TimePoint(23, 0, 2), TimePoint(23, 1, 3))
        val test2 = SingleBotCase(Map(map), Point(24, 0), Point(0, 7), listOf(Obstacle(obstacle1, 0.1), Obstacle(obstacle2, 0.2)))

        val res = SIPP().findPath(test2)

        test2.printWithPath(res!!)
    }

    @Test
    fun test_moscow_dij() {
        val test = SingleBotCase.fromFile("tests/Moscow_2_256.map", "tests/Moscow_2_256", Point(0, 0), Point(7, 97))

        Timer.start()
        val res = DijkstraWithTimeDimension().findPath(test)!!
        println("Dijkstra time: ${Timer.get()}")

        test.printWithPath(res)
    }

    @Test
    fun test_moscow_sipp() {
        val test = SingleBotCase.fromFile("tests/Moscow_2_256.map", "tests/Moscow_2_256", Point(0, 0), Point(7, 97))

        Timer.start()
        val res = SIPP().findPath(test)!!
        println("Sipp time: ${Timer.get()}")

        test.printWithPath(res)


        test.emulateToFile(res, "moscow_steps.txt")
    }
}