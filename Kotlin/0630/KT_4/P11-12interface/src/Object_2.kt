import kotlin.random.Random

// Mock Fragment class since we aren't in a full Android environment
open class Fragment

class RandomNumber {
    companion object {
        fun getRandomNumber(_num: Int = 1): IntArray {
            val num = if (_num < 1) 1 else _num
            val numbers = IntArray(num).apply {
                for (i in this.indices) {
                    this[i] = (1..5).random()
                }
            }
            return numbers
        }
    }
}

class NewDice {
    val rollValue: Int
        get() = (1..6).random() // Optimized from shuffled().first()

    companion object {
        fun randomValue() = println("Random value = ${(0..50).random()}")
    }
}

class User(val name: String) {
    companion object {
        const val TAG = "MyTag"
        fun create(name: String): User {
            return User(name)
        }
    }

    fun userInfo() {
        println("TAG = $TAG, name = $name")
    }
}

// Fixed spelling: Fragment instead of Fregment
class DetailFragment : Fragment() {
    // Fixed spelling: param1 & param2 instead of parm1 & parm2
    var param1: String? = null
    var param2: String? = null

    companion object {
        // Fixed spelling: newInstance instead of nuewInsttance
        fun newInstance(_param1: String, _param2: String): DetailFragment {
            return DetailFragment().apply {
                param1 = _param1
                param2 = _param2
            }
        }
    }
}

fun main() {
    // 1. Random Number Demo
    val numbers = RandomNumber.getRandomNumber(5)
    for (i in 0 until numbers.size) {
        println("numbers[$i] = ${numbers[i]}")
    }

    println()

    // 2. User Factory Demo
    val user = User.create("Alice")
    user.userInfo()

    println()

    // 3. Fragment Factory Demo
    val myFrag = DetailFragment.newInstance("Value 1", "Value 2")
    println("Fragment param1 = ${myFrag.param1}")
    println("Fragment param2 = ${myFrag.param2}")
}