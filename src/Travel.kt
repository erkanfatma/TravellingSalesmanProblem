//Defined to create travel that consist of list of nodes that were visited.
class Travel internal constructor(var cost: Double, var path: MutableList<Province>) {

    //Add new province to calculate distance
    fun AttachNewProvince(province: Province) {
        path.add(province)
        val initialCoordinate = path[path.size - 1].coordinate
        val finalCoordinate: Coordinate
        finalCoordinate = if ((path.size - 2) >= 0) path[path.size - 2].coordinate else path[0].coordinate
        val distance = initialCoordinate
            .DistanceCalculator(finalCoordinate)
        cost += distance
    }

     //Change provinces depend on the indices.
    fun ChangeProvinces(change: IntArray) {
        val x = change[0]
        val y = change[1]
        val temp = path[x]
        path[x] = path[y]
        path[y] = temp
        ReCalculateCost(x, y)
    }

    //Update cost depend on coordinates
    private fun ReCalculateCost(x: Int, y: Int) {
        var cost = 0.0
        for (i in 0 until path.size - 1) {
            cost += (path[i].coordinate).DistanceCalculator(path[i + 1].coordinate)
        }
        this.cost = cost
    }

}