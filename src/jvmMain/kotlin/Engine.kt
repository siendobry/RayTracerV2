import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.random.Random


class Engine(
    private val config : RenderConfiguration
) {

    private val entities = EntityList()
    private val lights = ArrayList<LightSource>()

    private fun colourRay(r : Ray, world : EntityList, lightSources : ArrayList<LightSource>, maxDepth : Int) : Colour {
        if (maxDepth <= 0) {
            return Colour(0.0, 0.0, 0.0)
        }
        val closestHit = world.getClosestHit(r, 0.001, Double.MAX_VALUE)
        if (closestHit != null) {
            var finalColour = Colour(0.0, 0.0, 0.0)
            for (lightSource in lightSources) {
                finalColour = finalColour + lightSource.enlighten(closestHit, world)
            }
            val scatteredRay = Ray()
            if (closestHit.material.scatter(closestHit, scatteredRay)) {
                finalColour = finalColour + mix(closestHit.material.albedo, colourRay(scatteredRay, world, lightSources, maxDepth - 1))
            }
            return finalColour.getClamped()
        }
        return Colour(0.0, 0.0, 0.0)
    }

    @Synchronized private fun addColour(colours : ArrayList<Colour>, idx : Int, colour : Colour) {
        colours[idx] = colours[idx] + colour
    }

    fun run() : ArrayList<Colour> {
        try {
            val imageWidth = config.width / config.pixelWidth
            val imageHeight = config.height / config.pixelHeight

            val camera = Camera(
                config.lookFrom,
                config.lookAt,
                Vector3d(0.0, 1.0, 0.0),
                config.fov,
                config.width.toDouble() / config.height,
                config.aperture,
                config.focusDist
            )

            entities.add(WorldElementsParser.parseEntities(config.filenameEntities))
            lights.addAll(WorldElementsParser.parseLights(config.filenameLights))

            val materialGround = Diffuse(Colour(0.8, 0.8, 0.0))
            val materialCenter = Diffuse(Colour(0.1, 0.2, 0.5))
            val materialLeft = Dielectric(Colour(1.0, 0.7, 0.7), 1.5)
            val materialRight = Metal(Colour(0.8, 0.6, 0.2), 1.0)
            val metal = Metal(Colour(0.8, 0.8, 0.8), 1.0)
//        world.add(Sphere(Point3d(0.0, 0.0, -1.0), 0.5, materialCenter))

            // metal cube
//            entities.add(Triangle(Point3d(-0.5, -0.5, -0.5), Point3d(-0.5, 0.5, -0.5), Point3d(0.5, -0.5, -0.5), metal))
//            entities.add(Triangle(Point3d(0.5, -0.5, -0.5), Point3d(-0.5, 0.5, -0.5), Point3d(0.5, 0.5, -0.5), metal))
//            entities.add(Triangle(Point3d(-0.5, 0.5, -0.5), Point3d(-0.5, 0.5, -1.5), Point3d(0.5, 0.5, -0.5), metal))
//            entities.add(Triangle(Point3d(0.5, 0.5, -0.5), Point3d(-0.5, 0.5, -1.5), Point3d(0.5, 0.5, -1.5), metal))
//            entities.add(Triangle(Point3d(-0.5, -0.5, -1.5), Point3d(-0.5, 0.5, -1.5), Point3d(-0.5, -0.5, -0.5), metal))
//            entities.add(Triangle(Point3d(-0.5, -0.5, -0.5), Point3d(-0.5, 0.5, -1.5), Point3d(-0.5, 0.5, -0.5), metal))
//            entities.add(Triangle(Point3d(0.5, -0.5, -0.5), Point3d(0.5, 0.5, -0.5), Point3d(0.5, -0.5, -1.5), metal))
//            entities.add(Triangle(Point3d(0.5, -0.5, -1.5), Point3d(0.5, 0.5, -0.5), Point3d(0.5, 0.5, -1.5), metal))
//            entities.add(Triangle(Point3d(0.5, -0.5, -1.5), Point3d(0.5, 0.5, -1.5), Point3d(-0.5, -0.5, -1.5), metal))
//            entities.add(Triangle(Point3d(-0.5, -0.5, -1.5), Point3d(0.5, 0.5, -1.5), Point3d(-0.5, 0.5, -1.5), metal))
//
//            entities.add(Sphere(Point3d(1.0, 0.0, -1.0), 0.5, materialCenter))
//            entities.add(Sphere(Point3d(-1.0, 0.0, -1.0), 0.5, materialLeft))
//            entities.add(Sphere(Point3d(-1.0, 0.0, -1.0), -0.45, materialLeft))
//            entities.add(Sphere(Point3d(0.0, -100.5, -1.0), 100.0, materialGround))
//            entities.add(Sphere(Point3d(0.0, 0.0, -0.25), 0.25, metal))
//        world.add(Plane(Point3d(0.0, -0.5, -1.0), Vector3d(0.0, -1.0, 0.0), materialGround))

//            entities.add(WorldElementsParser.parseEntities("entities.txt"))

//            lights.add(LightSource(Point3d(3.0, 10.0, 5.0), Colour(1.0, 1.0, 1.0)))
//        lightSources.add(LightSource(Point3d(0.0, 10.0, -3.0), Colour(1.0, 1.0, 1.0)))

            val pixels = ArrayList<Colour>()
            for (j in imageHeight - 1 downTo 0) {
                for (i in 0 until imageWidth) {
                    pixels.add(Colour(0.0, 0.0, 0.0))
                }
            }
            val executor = Executors.newCachedThreadPool()
            val tasks = ArrayList<Future<*>>()

            for (k in 0 until config.samplesPerPixel) {
                tasks.add(executor.submit {
                    for (j in imageHeight - 1 downTo 0) {
                        for (i in 0 until imageWidth) {
                            val u = (i.toDouble() + Random.nextDouble()) / (imageWidth - 1)
                            val v = (j.toDouble() + Random.nextDouble()) / (imageHeight - 1)
                            val r = camera.getRay(u, v)
                            // atomic task
                            addColour(pixels, (imageHeight - 1 - j) * imageWidth + i, colourRay(r,
                                entities, lights, config.maxRecurDepth))
                        }
                    }
                })
            }
            // wait for all threads to end
            tasks.forEach {
                it.get()
            }
            executor.shutdown()
            pixels.forEach { it.divBySamples(config.samplesPerPixel) }
            return pixels
        }
        catch (ex : Exception) {
            ex.printStackTrace()
            throw RuntimeException(ex.message)
        }
    }

}