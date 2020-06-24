import java.io.File
import java.io.IOException
import java.util.*

object TravellingSalesmanProblem {
    @JvmStatic
    fun main(args: Array<String>) {
        var province: List<Province> = ArrayList()
        val fileRead = FileReader()
        /*
         * Get provinces from files named instance.txt
         */
        val filePath = "src/instance.txt"
        val file = File(filePath)
            province = fileRead.GetProvincesFromFile(file)


        val searchAlgorithm = LinearSearch()
        var travel: Travel = searchAlgorithm.DefineSimulatedAnnealing(province)
        val path: List<Province> = travel.path
        for (province in path) println(province.provinceName + "\t")
        println()
        println("Cost of travel = " + travel.cost)
    }
}