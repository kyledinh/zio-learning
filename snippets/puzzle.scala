import collection.mutable.BitSet
import scala.io.Source

@main
def main(filename : String): Unit = {
    val sudoku = load(filename)
    val neighbors = (0 to 80).toVector.map(neighborsOf)
    println(sudoku)
    val s2 = scanAndMark(sudoku, neighbors)
    println(s2)
}

def scanAndMark(sudoku : Vector[BitSet], neighbors : Vector[Vector[Int]]) : Vector[BitSet] = {
    val next = sudoku.zipWithIndex.map { case (bset, index) => 
        if bset.count(z=>true) > 1 then {
            // go thru neighbors and delete from BitSet 
            neighbors(index).foreach(i => {
                if sudoku(i).count(z=>true) == 1 then {
                    val target = sudoku(i).sum // on only 1 element 
                    var newbset = bset
                    if bset.contains(i) then newbset = bset - target
                    println(s" index ${index} :: target ${target} :: ${newbset}")
                }
            }) 
        }
        bset 
    }
    return next
}

def getRowAndColumn(index : Int): (Int, Int) = {
    val row = if index < 1 then 0 else index / 9 
    val column = index % 9 
    (row, column)
}

def neighborsOf(index: Int): Vector[Int] = {
    (0 to 80).filter(isNeighbor(_, index)).toVector
}

def isNeighbor(index: Int, target : Int) : Boolean = {
    val (row, column) = getRowAndColumn(index)
    val (trow, tcolumn) = getRowAndColumn(target)
    index != target && (row == trow || column == tcolumn)
}

def load(filename: String): Vector[BitSet] = {
    val start = Source.fromFile(filename).getLines.mkString.replaceAll("\\s", "").split(",").map(_.toInt).toVector
    val grid = start.map { n =>
        if n > 0 then BitSet(n)
        else BitSet(1,2,3,4,5,6,7,8,9)
    }
    return grid
}

    