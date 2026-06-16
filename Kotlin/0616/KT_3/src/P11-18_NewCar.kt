fun main() {

    val myCar = Car(brand = "Toyota", model = "RAV4", year = 2022
    )
    myCar.displayInfo()

    println()

    val oilCar = OilCar(_brand = "BMW", _model = "X3", _year = 2025
    )
    oilCar.displayInfo()
    oilCar.oil(oilCap = 500, oilConsume = 15)

    println()

    val electricCar = ElectricCar(brand = "Tesla", model = "Model 3", year = 2026, battery = 50
    )
    electricCar.displayInfo()
    electricCar.charge(amount = 30)

    println()

    val carList = mutableListOf<Car>(myCar,oilCar,electricCar)
    carList[0].displayInfo()
    println()
    carList[1].displayInfo()
    println()
    carList[2].displayInfo()

//    for (car in carList) {
//        car.displayInfo()
//        println()
//
//    }
}

open class Car(
    val brand: String,
    val model: String,
    val year: Int
) {

    fun displayInfo() {
        println("Car brand : $brand, model :$model, year: $year")
    }
}

class OilCar(
    _brand: String,
    _model: String,
    _year: Int
) : Car(_brand, _model, _year) {

    fun oil(
        oilCap: Int,
        oilConsume: Int
    ) {
        println("$brand $model can drive ${oilCap * oilConsume} km")
    }
}

class ElectricCar(
    brand: String,
    model: String,
    year: Int,
    battery: Int
) : Car(brand, model, year) {

    var batteryCappacity = battery

    fun charge(amount: Int) {
        batteryCappacity =
            (batteryCappacity + amount).coerceAtMost(100)

        println("$brand $model charged to $batteryCappacity")
    }
}