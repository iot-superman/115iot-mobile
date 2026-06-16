fun main() {

    // https://chatgpt.com/s/m_6a30bfbfeb208191a16323d1e89dad8c

    var info = Info("Mary",22)
    info.dataPrint()

    info =Info("John", 30, "john@gmail.com")
    info.dataPrint()

    info= Info("Jack",33, "jack@gmail.com", "22222")






    println()
    var carInfo = CarInfo(
        brand = "BMW",
        model = "X3",
        year = 2025
    )

    carInfo.carPrint()

    carInfo.speed = 50

    carInfo.accelerate(20)

    carInfo.carPrint()

}


class CarInfo {

    var brand: String
    var model: String
    var year: Int
    var speed: Int

    //================================
    // 主建構子
    //================================
    constructor(
        brand: String,
        model: String,
        year: Int
    ) {
        this.brand = brand
        this.model = model
        this.year = year
        this.speed = 0
    }

    //================================
    // 次建構子
    // 沒給 year 時預設今年
    //================================
    constructor(
        brand: String,
        model: String
    ) : this(
        brand,
        model,
        2025
    )

    //================================
    // 加速
    //================================
    fun accelerate(increase: Int) {
        speed += increase
    }

    //================================
    // 顯示資料
    //================================
    fun carPrint() {
        println("Brand : $brand")
        println("Model : $model")
        println("Year  : $year")
        println("Speed : $speed")
        println("====================")
    }
}



