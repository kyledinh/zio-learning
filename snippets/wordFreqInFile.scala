import scala.io.Source

@main 
def WordFreq(filename : String) = {
	val chapters = Source.fromFile(filename).getLines.toVector
	val tokens = chapters.flatMap(_.split("\\W")).filterNot(_.isEmpty)
	val freqs = tokens.groupBy ( w => w).mapValues(_.size)
	// sort ascending 
	val sorted = freqs.toSeq.sortBy(_._2)
	// print 10 most frequent
	for (w <- sorted.takeRight(10)) { println(w) }
}
