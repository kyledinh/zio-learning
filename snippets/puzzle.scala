import collection.mutable.BitSet
import scala.io.Source

@main
def main(filename : String): Unit = {
    val sudoku = load(filename)
    val neighbors = (0 to 80).toVector.map(neighborsOf)

    printAsGrids(sudoku)

    var turnCount = 0
    var maxTurns = 100
    var previousSolveCount = 0
    var currentSolveCount = solvedCount(sudoku)

    while (solvedCount(sudoku) < 81 && turnCount < maxTurns && previousSolveCount != currentSolveCount) do {
        previousSolveCount = solvedCount(sudoku)
        scanAndMark(sudoku, neighbors)
        turnCount += 1
        currentSolveCount = solvedCount(sudoku)
        println()
        println(s"Turn count: ${turnCount}")
        printAsGrids(sudoku)
    } 

    if (solvedCount(sudoku) == 81) { println(s"SOLVED in ${turnCount} turns!") }
    if (solvedCount(sudoku) < 81) { println(s"STOPPED at ${turnCount} turns!") }
    printAsGrids(sudoku)
}

def solvedCount(sudoku: Vector[BitSet]): Int = {
    sudoku.count(bitset => bitset.size == 1)
}

def printAsGrids(sudoku: Vector[BitSet]): Unit = {
    println(s"==== GRID (${solvedCount(sudoku)})====")
    for (row <- 0 to 8) println(s" ${getSudokuRow(sudoku,row)}") 
    println()
}

def getSudokuRow(sudoku: Vector[BitSet], row: Int) : String = {
    val start = (row * 9) + 0 
    val end = start + 9
    sudoku.slice(start, end).map( b => s"${getSudokuValue(b)}").mkString(" ")
}

def getSudokuValue(bitset: BitSet): String = {
    if bitset.size == 1 then bitset.head.toString else "."
}

def scanAndMark(sudoku : Vector[BitSet], neighbors : Vector[Vector[Int]]): Unit = {
    for (index <- 0 to 80) {
        val cell = sudoku(index)
        if (cell.size > 1) {
            val ones: BitSet = neighbors(index)
                .map(sudoku(_))
                .filter(_.size == 1)
                .map(_.head)
                .foldLeft(BitSet())(_ + _)
            val before = BitSet().addAll(cell)
            cell &~= ones // removes ones from cell
            if (sudoku(index)!= before) {
                println(s"====== changed index $index")
                println(s"index: $index ones: $ones before: $before after: ${sudoku(index)}")
            }
            if (sudoku(index).size == 1) println(s"!!!!!! solved index $index")
        }
    }
}

def getRowAndColumn(index : Int): (Int, Int) = {
    val row = if index < 1 then 0 else index / 9 
    val column = index % 9 
    (row, column)
}

def getSubBlock(index: Int) : String = {
    index match {
        case index if Vector(0,1,2,9,10,11,18,19,20).contains(index) => "BLOCK_0"
        case index if Vector(3,4,5,12,13,14,21,22,23).contains(index) => "BLOCK_1"
        case index if Vector(6,7,8,15,16,17,24,25,26).contains(index) => "BLOCK_2"
        case index if Vector(27,28,29,36,37,38,45,46,47).contains(index) => "BLOCK_3"
        case index if Vector(30,31,32,39,40,41,48,49,50).contains(index) => "BLOCK_4"
        case index if Vector(33,34,35,42,43,44,51,52,53).contains(index) => "BLOCK_5"
        case index if Vector(54,55,56,63,64,65,72,73,74).contains(index) => "BLOCK_6"
        case index if Vector(57,58,59,66,67,68,75,76,77).contains(index) => "BLOCK_7"
        case index if Vector(60,61,62,69,70,71,78,79,80).contains(index) => "BLOCK_8"
        case _ => "OUTSIDE"
    }
}

def neighborsOf(index: Int): Vector[Int] = {
    (0 to 80).filter(isNeighbor(_, index)).toVector
}

def isNeighbor(index: Int, target : Int) : Boolean = {
    val (row, column) = getRowAndColumn(index)
    val (trow, tcolumn) = getRowAndColumn(target)
    index != target && (row == trow || column == tcolumn || getSubBlock(index) == getSubBlock(target))
}

def load(filename: String): Vector[BitSet] = {
    val start = Source.fromFile(filename).getLines.mkString.replaceAll("\\s", "").split(",").map(_.toInt).toVector
    val grid = start.map { n =>
        if n > 0 then BitSet(n)
        else BitSet(1,2,3,4,5,6,7,8,9)
    }
    return grid
}
