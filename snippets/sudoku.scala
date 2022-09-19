import collection.mutable.BitSet
import scala.io.Source
import util.control.Breaks._

@main
def main(filename : String): Unit = {
    val sudoku = Sudoku(filename)
    sudoku.toString()
}

class Sudoku(grid : Vector[BitSet]) {
    import Sudoku._

    var grid: Vector[BitSet] = new Vector[BitSet]
    // def solve(): Sodoku = {
    //     ???
    // }
}

object Sudoku {
    val neighbors = (0 to 80).toVector.map(neighborsOf)

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
    
    override def toString() : String  = {
        grid.println()
    } 

    def apply(filename: String): Sudoku = {
      val start = Source.fromFile(filename).getLines.mkString.replaceAll("\\s", "").split(",").map(_.toInt).toVector
      var grid = start.map { n =>
        if n > 0 then BitSet(n)
        else BitSet(1,2,3,4,5,6,7,8,9)
      }
      new Sudoku(grid)
    }
}

    