import scala.io.{BufferedSource, Codec}
import java.io.File
import models._
import parsers._

object Benchmark {
  def main(args: Array[String]): Unit = {

    val filename = "all"
    val file = new File(s"/home/julien/work/perso/json-benchmark/test-data/$filename")

    val readers = Map(
      "play" -> PlayNativeReader,
      "play-jawn" -> PlayJawnReader,
      "circe-jawn" -> CirceReader,
      "json4s" -> Json4sNativeReader,
      "json4s-jackson" -> Json4sJacksonReader,
      "json4s-jawn" -> Json4sJawnReader,
      "argonaut-jawn" -> ArgonautReader,
      "spray" -> SprayNativeReader,
      "spray-jawn" -> SprayJawnReader
    )

    readers.values.foreach(read(file, _))
  }

  private def read(file: File, reader: BidRequestReader) = {
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
