import java.io.File
import java.io.IOException
import java.util.*

class FileReader {

     // This section is created to get provinces and coordinates from txt file
    @Throws(IOException::class)
    fun GetProvincesFromFile(file: File): List<Province> {
        val sc = Scanner(file)
        val provinceList: MutableList<Province> = ArrayList()
        while (sc.hasNext()) {
            val line = sc.nextLine().split(" ".toRegex()).toTypedArray()
            if (line.size == 1) continue
            val xCoordinate: Int = line[1].toInt()
            val yCoordinate: Int = line[2].toInt()
            val coordinates = Coordinate(xCoordinate, yCoordinate)
            val province = Province(line[0], coordinates)
            provinceList.add(province)
        }
        return provinceList
    }
}