data class RenderConfiguration(
    val width : Int,
    val height : Int,
    val pixelWidth : Int,
    val pixelHeight : Int,
    val samplesPerPixel : Int,
    val maxRecurDepth : Int,
    val lookFrom : Point3d,
    val lookAt : Point3d,
    val fov : Int,
    val aperture : Double,
    val focusDist : Double,
    val filenameEntities : String,
    val filenameLights : String
)