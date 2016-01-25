import scala.io.Codec
import java.io.File

import models._

object Main {

  def main(args: Array[String]): Unit = {

    val filename = "1log"
    val file = new File(s"/home/julien/work/perso/json-benchmark/test-data/$filename")

    benchs.Referential.readers.values.foreach(run(file, _))
  }

  private def run(file: File, reader: BidRequestReader) = {
    val source = scala.io.Source.fromFile(file)(Codec.UTF8)
    println(s"Running ${reader.getClass.getName}")

    val start = System.nanoTime()
    val result = source.getLines().foldLeft(ParsingResult(0, 0, 0)) { case (r, line) =>
      reader.parse(line, r)
    }
    val end = System.nanoTime()
    source.close()

    println(result + " in " + ((end - start) / 1000000) + "ms")
  }


}
