import java.io.BufferedReader
import java.io.FileReader

class WorldElementsParser {

    companion object {
        fun parseEntities(filename : String) : EntityList {
            val entities = EntityList()
            if (filename == "") return entities
            try {
                val reader = BufferedReader(FileReader(filename))
                var line = reader.readLine()
                while (line != null) {
                    entities.add(Entity.parse(line))
                    line = reader.readLine()
                }
                return entities
            } catch (ex : Exception) {
                throw IllegalArgumentException(ex.message)
            }
        }

        fun parseLights(filename : String) : ArrayList<LightSource> {
            val lights = ArrayList<LightSource>()
            if (filename == "") return lights
            try {
                val reader = BufferedReader(FileReader(filename))
                var line = reader.readLine()
                while (line != null) {
                    lights.add(LightSource.parse(line))
                    line = reader.readLine()
                }
                return lights
            }
            catch (ex : Exception) {
                throw IllegalArgumentException(ex.message)
            }
        }
    }

}