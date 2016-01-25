package benchs

import scala.io.Codec
import java.io.File

import org.openjdk.jmh.annotations._

import models.{ParsingResult, BidRequestReader}

@State(Scope.Benchmark)
abstract class ReadersBenchmark(file: File) {

  def run(file: File, reader: BidRequestReader): Unit = {
    val source = scala.io.Source.fromFile(file)(Codec.UTF8)
    val result = source.getLines().foldLeft(ParsingResult(0, 0, 0)) { case (r, line) =>
      reader.parse(line, r)
    }
    source.close()

    assert(result.cannotParse == 0)
    assert(result.cannotUnmarshal == 0)
    assert(result.ok > 0)
  }

  @Param(Array("play", "play-jawn", "circe-jawn", "json4s", "json4s-jackson", "json4s-jawn", "argonaut-jawn", "spray", "spray-jawn"))
  var readerName: String = _

  @Benchmark
  def bench(): Unit = {
    val reader = Referential.readers(readerName)
    val source = scala.io.Source.fromFile(file)(Codec.UTF8)
    val result = source.getLines().foldLeft(ParsingResult(0, 0, 0)) { case (r, line) =>
      reader.parse(line, r)
    }
    source.close()

    assert(result.cannotParse == 0)
    assert(result.cannotUnmarshal == 0)
    assert(result.ok > 0)
  }

}

//class OneLineReadersbenchmarks extends ReadersBenchmark(new File(s"/home/julien/work/perso/json-benchmark/test-data/1log"))
class OneFileReadersbenchmarks extends ReadersBenchmark(new File(s"/home/julien/work/perso/json-benchmark/test-data/1"))
//class AllFilesReadersbenchmarks extends ReadersBenchmark(new File(s"/home/julien/work/perso/json-benchmark/test-data/all"))
