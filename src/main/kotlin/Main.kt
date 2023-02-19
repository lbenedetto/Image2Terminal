import org.fusesource.jansi.Ansi.ansi
import org.fusesource.jansi.AnsiConsole
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    AnsiConsole.systemInstall()
    val file = File(args[0])
    val image = ImageIO.read(file)

    var state = State.DEFAULT
    val ansi = ansi().reset()
    val height = image.height
    val width = image.width
    for (y in 0 until height - 1 step 2) {
        for (x in 0 until width) {
            val topPixel = RGBa(image.getRGB(x, y))
            val bottomPixel = if (y + 1 < height) {
                RGBa(image.getRGB(x, y + 1))
            } else {
                RGBa()
            }
            state = ansi.mixed(state, topPixel, bottomPixel)
        }
        state = ansi.updateIfNeeded(state, State.DEFAULT) {
            it.newline()
        }
    }

    val stringOut = ansi.toString()
    println(stringOut)
    val outputFile = file.toPath().parent
        .resolve("${file.nameWithoutExtension}.bash")
        .toFile()
    outputFile.setExecutable(true)
    outputFile.writeText(
"""
#!/usr/bin/env bash
echo -e ${"\"\"\""}
$stringOut
${"\"\"\""}
""".trimIndent())
    println("Output saved to ${file.nameWithoutExtension}.bash")
}

data class RGBa(val red: Int, val green: Int, val blue: Int, val transparent: Boolean) {
    constructor(rgb: Int) : this(Color(rgb, true))
    constructor(color: Color) : this(color.red, color.green, color.blue, color.alpha < 30)
    constructor() : this(0, 0, 0, true)
}
