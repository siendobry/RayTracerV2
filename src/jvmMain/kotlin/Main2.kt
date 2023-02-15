fun main() {
    val engine = Engine(
        RenderConfiguration(
            400,
            225,
            1,
            1,
            100,
            50,
            Point3d(0.0, 0.0, 0.0),
            Point3d(0.0, 0.0, -1.0),
            90,
            0.0,
            1.0,
            "entities.txt",
            "lights.txt"
        )
    )
    engine.run()
}