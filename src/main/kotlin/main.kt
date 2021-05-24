
fun main() {
    val map2 = """
        ............#######......
        ............#######......
        ....####.................
        .............#####.......
        .........................
        ....#####################
        .........................
        .........................
    """.trimIndent()

    val test2 = SingleBotCase(Map(map2), Point(24, 0), Point(0, 7), 0.1, emptyList())

    val res = DijkstraWithTimeDimension().findPath(test2)

    test2.printWithPath(res!!)


}