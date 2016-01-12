import scala.io.Codec
import java.io.File
import models._
import parsers._

object Benchmark {
  def main(args: Array[String]): Unit = {

    val filename = "1log"
    val file = new File(s"/home/julien/work/projects/json-benchmark/test-data/$filename")

    val runOnlyOneParser = args.headOption

    val parsers = Map(
      "play" -> PlayReader,
      "circe" -> CirceReader,
      "json4s-native" -> Json4sSemiAutoNativeReader,
      "json4s-jackson" -> Json4sSemiAutoJacksonReader,
      "argonaut" -> ArgonautReader
    )

    def runForFile(parser: BidRequestParser) = run(file, parser)

    runOnlyOneParser match {
      case Some(lib) if parsers.contains(lib) => runForFile(parsers(lib))
      case Some(lib) if !parsers.contains(lib) => sys.error(s"Unknown parser $lib")
      case _ => parsers.values.foreach(runForFile)
    }

  }

  private def run(file: File, parser: BidRequestParser) = {
    val source = scala.io.Source.fromFile(file)(Codec.UTF8)
    println(s"Running ${parser.getClass.getName}")

    val start = System.nanoTime()
    val result = source.getLines().zipWithIndex.foldLeft(ParsingResult(0, 0, 0)) { case (r, (line, index) ) =>
      parser.parse(index, line, r)
    }
    val end = System.nanoTime()
    source.close()

    println(result + " in " + ((end - start) / 1000000) + "ms")
  }
}
