
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App(openNewWindow: (String, RenderConfiguration) -> Unit) {

    var title by remember { mutableStateOf("Render") }
    var width by remember { mutableStateOf(400) }
    var height by remember { mutableStateOf(225) }
    var pixelWidth by remember { mutableStateOf(1) }
    var pixelHeight by remember { mutableStateOf(1) }
    var samplesPerPixel by remember { mutableStateOf(100) }
    var maxRecurDepth by remember { mutableStateOf(50) }

    var lookFromX by remember { mutableStateOf(-2.0) }
    var lookFromY by remember { mutableStateOf(1.0) }
    var lookFromZ by remember { mutableStateOf(1.0) }
    var lookAtX by remember { mutableStateOf(0.0) }
    var lookAtY by remember { mutableStateOf(0.0) }
    var lookAtZ by remember { mutableStateOf(-1.0) }
    var fov by remember { mutableStateOf(40) }
    var aperture by remember { mutableStateOf(0.001) }
    var focusDist by remember { mutableStateOf(1.0) }

    var filenameEntities by remember { mutableStateOf("entities.txt") }
    var filenameLights by remember { mutableStateOf("lights.txt") }

    Column {
        Row {
            Column {
                OutlinedTextField(
                    value = title,
                    label = { Text(text = "Title (String)") },
                    onValueChange = {
                        title = it
                    }
                )
                OutlinedTextField(
                    value = width.toString(),
                    label = { Text(text = "Width (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        width = it.toInt()
                    }
                )
                OutlinedTextField(
                    value = height.toString(),
                    label = { Text(text = "Height (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        height = it.toInt()
                    }
                )
                OutlinedTextField(
                    value = pixelWidth.toString(),
                    label = { Text(text = "Pixel width (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        pixelWidth = it.toInt()
                    }
                )
                OutlinedTextField(
                    value = pixelHeight.toString(),
                    label = { Text(text = "Pixel height (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        pixelHeight = it.toInt()
                    }
                )
                OutlinedTextField(
                    value = samplesPerPixel.toString(),
                    label = { Text(text = "Samples per pixel (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        samplesPerPixel = it.toInt()
                    }
                )
                OutlinedTextField(
                    value = maxRecurDepth.toString(),
                    label = { Text(text = "Max recursion depth (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        maxRecurDepth = it.toInt()
                    }
                )

            }
            Column {
                OutlinedTextField(
                    value = lookFromX.toString(),
                    label = { Text(text = "Look from (x) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        lookFromX = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = lookFromY.toString(),
                    label = { Text(text = "Look from (y) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        lookFromY = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = lookFromZ.toString(),
                    label = { Text(text = "Look from (z) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        lookFromZ = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = lookAtX.toString(),
                    label = { Text(text = "Look at (x) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        lookAtX = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = lookAtY.toString(),
                    label = { Text(text = "Look at (y) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        lookAtY = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = lookAtZ.toString(),
                    label = { Text(text = "Look at (z) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        lookAtZ = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = fov.toString(),
                    label = { Text(text = "Field of view (2 - 178) (Int)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        fov = it.toInt()
                    }
                )
                OutlinedTextField(
                    value = aperture.toString(),
                    label = { Text(text = "Aperture (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        aperture = it.toDouble()
                    }
                )
                OutlinedTextField(
                    value = focusDist.toString(),
                    label = { Text(text = "Focus distance (>0) (Double)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        focusDist = it.toDouble()
                    }
                )
            }

            Column {
                OutlinedTextField(
                    value = filenameEntities,
                    label = { Text(text = "Path to file with entities (with .txt suffix) (String)") },
                    onValueChange = {
                        filenameEntities = it
                    }
                )
                OutlinedTextField(
                    value = filenameLights,
                    label = { Text(text = "Path to file with light sources (with .txt suffix) (String)") },
                    onValueChange = {
                        filenameLights = it
                    }
                )
            }
        }
        Row {
            Button(
                onClick = {
                    openNewWindow(
                        title,
                        RenderConfiguration(
                            width,
                            height,
                            pixelWidth,
                            pixelHeight,
                            samplesPerPixel,
                            maxRecurDepth,
                            Point3d(lookFromX, lookFromY, lookFromZ),
                            Point3d(lookAtX, lookAtY, lookAtZ),
                            fov,
                            aperture,
                            focusDist,
                            filenameEntities,
                            filenameLights
                        )
                    )
                }
            ) {
                Text("Open new window")
            }
        }
    }
}

@Composable
private fun ApplicationScope.ImageWindow(
    state : ImageWindowState
) = Window(
    onCloseRequest = state::close,
    title = state.title,
    state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified)
) {
    val pixels: ArrayList<Colour>
    try {
        pixels = Engine(state.config).run()
    }
    catch (ex : Exception) {
        ex.printStackTrace()
        throw RuntimeException(ex.message)
    }

    Canvas(
        modifier = Modifier
            .width(with(LocalDensity.current) { state.config.width.toDp() })
            .height(with(LocalDensity.current) { state.config.height.toDp() })
    ) {
        for (i in 0 until pixels.size) {
            drawRect(
                color = pixels[i].getClamped().toColor(),
                size = Size(1f, 1f),
                topLeft = Offset((i % state.config.width).toFloat(), (i / state.config.width).toFloat())
            )
        }
    }
}

private class ImageAppState {
    val windows = mutableStateListOf<ImageWindowState>()

    fun openNewWindow(title : String, config : RenderConfiguration) {
        windows += ImageWindowState(title, config)
    }

    private fun ImageWindowState(
        title : String,
        config: RenderConfiguration
    ) = ImageWindowState(
        title,
        config,
        windows::remove
    )
}

private class ImageWindowState(
    val title : String,
    val config : RenderConfiguration,
    private val close : (ImageWindowState) -> Unit
) {
    fun close() = close(this)
}


fun main() = application {
    val appState = remember { ImageAppState() }

    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified)
    ) {
        App(appState::openNewWindow)
    }

    for (window in appState.windows) {
        key(window) {
            ImageWindow(window)
        }
    }
}
