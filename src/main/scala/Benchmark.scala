import java.io.File
import models.ParsingResult
import parsers.BidRequestParser
import parsers.argonaut.ArgonautReader
import parsers.circe.CirceReader
import parsers.json4s.Json4sSemiAutoReader
import parsers.playjson.PlayReader

object Benchmark {
  def main(args: Array[String]): Unit = {

    val filename = "1log"
    val file = new File(s"/home/julien/work/projects/json-benchmark/test-data/$filename")

    val lib = args.headOption.getOrElse("play")
    val source = scala.io.Source.fromFile(file)

    val reader: BidRequestParser = lib match {
      case "play" => PlayReader
      case "circe" => CirceReader
      case "json4s" => Json4sSemiAutoReader
      case "argonaut" => ArgonautReader
      case _ => sys.error("unknow library")
    }

    println(s"Starting benchmark with $lib and file ${file.getName}")
    val start = System.nanoTime()
    val total = source.getLines().zipWithIndex.foldLeft(ParsingResult(0, 0, 0)) { case (result, (line, index) ) =>
      reader.parse(index, line, result)
    }
    val end = System.nanoTime()
    source.close()

    println(total + " in " + ((end - start) / 1000000) + "ms")
  }
}
