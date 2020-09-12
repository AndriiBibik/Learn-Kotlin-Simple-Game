package game

fun main(args: Array<String>) {
    val game = Game()

    while (true) {
        print("Please enter a direction: NORTH, SOUTH, EAST, WEST: ")
        if (!game.makeMove(readLine())) break
    }

}

enum class Directions {
    NORTH, SOUTH, EAST, WEST, START, END
}

class Game {

    var path: MutableList<Directions> = mutableListOf(Directions.START)

    val location = Location()

    val north: () -> Boolean = {  -> path.add(Directions.NORTH)
                                    location.updateLocation(Directions.NORTH); location.printLocation()
                                    true }
    val south: () -> Boolean = { -> path.add(Directions.SOUTH)
                                    location.updateLocation(Directions.SOUTH); location.printLocation()
                                    true }
    val east: () -> Boolean = { -> path.add(Directions.EAST)
                                   location.updateLocation(Directions.EAST); location.printLocation()
                                   true }
    val west: () -> Boolean = { -> path.add(Directions.WEST)
                                   location.updateLocation(Directions.WEST); location.printLocation()
                                   true }
    val end: () -> Boolean = { -> path.add(Directions.END)
        print("Game over: "); println(path)
        path.clear()
        false
    }

    fun move(where: () -> Boolean): Boolean {
        return where()
    }

    fun makeMove(direction: String?): Boolean {

        if (direction != null) {

            if (Directions.NORTH.toString() == direction) {
                return move(north)
            } else if (Directions.WEST.toString() == direction) {
                return move(west)
            } else if (Directions.SOUTH.toString() == direction) {
                return move(south)
            } else if (Directions.EAST.toString() == direction) {
                return move(east)
            } else { // end
                return move(end)
            }

        } else {
            return move(end)
        }
    }

    class Location(val width: Int = 4,
                   val height: Int = 4) {

        val map = Array(width) { arrayOfNulls<String>(height) }

        var location = Pair(0,0)

        companion object{

            const val CITY = "City"
            const val LAWN = "Lawn"
            const val LAKE = "Lake"
            const val FOREST = "Forest"
            const val PRIVATE_PROPERTY = "Private property"

        }

        fun updateLocation(direction: Directions) {

            var xMove = 0
            var yMove = 0

            when(direction) {
                Directions.NORTH -> yMove += 1
                Directions.EAST -> xMove += 1
                Directions.SOUTH -> yMove -= 1
                Directions.WEST -> xMove -= 1
            }
            if (xMove != 0) {
                if ((location.first + xMove) < width && (location.first + xMove) >= 0 )
                    location = location.copy(first = location.first + xMove)
                else
                    println("You trying to go out of the map in the $direction direction")
            } else if (yMove != 0) {
                if ((location.second + yMove) < height && (location.second + yMove) >= 0 )
                    location = location.copy(second = location.second + yMove)
                else
                    println("You trying to go out of the map in the $direction direction")
            }
        }

        fun printLocation() {
            println("You at the ${map[location.first][location.second]} , on coordinates: [${location.first}, ${location.second}]")
        }

        init {
            map[0][0] = CITY
            map[0][1] = CITY
            map[0][2] = LAWN
            map[0][3] = LAWN

            map[1][0] = CITY
            map[1][1] = CITY
            map[1][2] = LAKE
            map[1][3] = FOREST

            map[2][0] = LAWN
            map[2][1] = LAWN
            map[2][2] = FOREST
            map[2][3] = FOREST

            map[3][0] = PRIVATE_PROPERTY
            map[3][1] = LAWN
            map[3][2] = FOREST
            map[3][3] = FOREST
        }
    }
}