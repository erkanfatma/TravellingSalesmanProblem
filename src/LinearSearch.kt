import java.lang.Math.exp
import java.util.*

class LinearSearch {

    //Create a travel list depend on province
    fun CreateRandomTravel(provinces: List<Province>): Travel {
        val restProvinceList: MutableList<Province> = ArrayList<Province>()
        restProvinceList.addAll(provinces!!)
        val begin = restProvinceList.removeAt(0)
        val cost = 0.0
        val way: MutableList<Province> = ArrayList()
        val travel = Travel(cost, way)
        travel.AttachNewProvince(begin)
        val rd = Random()
        val len = restProvinceList.size
        for (i in 0 until len) {
            var nextProvince = 0
            if (restProvinceList.size - 1 > 0) nextProvince = rd.nextInt(restProvinceList.size - 1)
            travel.AttachNewProvince(restProvinceList.removeAt(nextProvince))
        }
        travel.AttachNewProvince(begin)
        println()
        return travel
    }

    //To run simulated annealing in given province list
    protected fun SA(provinceList: List<Province>, initialTemp: Double, coolingValue: Double, k: Int): Travel {
        val numbersOfProvinces = provinceList.size
        var presentTravel = CreateRandomTravel(provinceList)
        var optimumTravel = Travel(presentTravel.cost, presentTravel.path)
        //If provinceList contains less than 3 element, then this is a optimum travel
        if (numbersOfProvinces <= 2) return optimumTravel
        // Create a cooling schedule
        val coolSchedule = CoolingPlan(initialTemp, coolingValue)
        val temperature = initialTemp
        //Create possible changes
        val randomChange = RandomChange(
            presentTravel.path.size, k)
        var counter = 0
        for (i in coolSchedule) {
            val newTravel = Travel(presentTravel.cost, presentTravel.path)
            val rand = Random()
            var move = randomChange[0]
            if (randomChange.size - 1 > 0) move = randomChange[rand.nextInt(randomChange.size - 1)]
            // Changing provinces
            newTravel.ChangeProvinces(move)
            // Control if this travel available
            if (AvailableTour(presentTravel, newTravel, i)) presentTravel = Travel(newTravel.cost, newTravel.path)

            //Reconfigure the optimum travel if needed
            if (presentTravel.cost < optimumTravel.cost) optimumTravel = Travel(presentTravel.cost, presentTravel.path)
            counter++
            if (counter % 25000 == 0);
        }
        return optimumTravel
    }

    // To define all length 2 combinations.
    private fun AvailableTour(presentTravel: Travel, newTravel: Travel, temp: Double): Boolean {
        if (newTravel.path.size != presentTravel.path.size) println("currentTour.size() = " + presentTravel.path.size + "  new tour size = " + newTravel.path.size)
        if (newTravel.path.size != presentTravel.path.size) return false
        val delta = newTravel.cost - presentTravel.cost
        return if (delta <= 0.0) true else {
            val possibility = exp(-delta / temp)
            possibility > Math.random()
        }
    }

    private fun RandomChange(size: Int, k: Int): List<IntArray> {
        val returnValue: MutableList<IntArray> = ArrayList()
        val intList: MutableList<Int> = ArrayList()
        for (j in 1 until size) intList.add(j)
        for (k in 0 until intList.size - 2) {
            val arrayList: ArrayList<Int?> = ArrayList<Int?>()
            arrayList.add(intList[k])
            for (j in k + 1 until intList.size - 1) {
                arrayList.add(intList[j])
                val values = IntArray(2)
                values[0] = arrayList[0]!!
                values[1] = arrayList[1]!!
                returnValue.add(values)
                arrayList.removeAt(arrayList.size - 1)
            }
        }
        return returnValue
    }

    fun CoolingPlan(initial: Double, coolingRate: Double): List<Double> {
        val plan: MutableList<Double> = ArrayList()
        var start = initial
        while (start >= 1) {
            plan.add(start)
            start -= coolingRate
        }
        return plan
    }

    // To define the simulated annealing
    fun DefineSimulatedAnnealing(provinceList: List<Province>): Travel {
        val initialTemp = 20000.0
        val coolingRate = 0.999985
        return SA(provinceList, initialTemp,
            coolingRate, 2)
    }
}