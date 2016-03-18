# Just another JSON scala benchmark 

> **Warning:** This benchmark is HEAVILY opiniated. It's only focus on the unmarshmalling of RTB bid-requests to a unified case-class representation.

## Candidates:

* [Play-JSON](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/play/src/main/scala/PlayReader.scala#L36) <br/>_Compile-time macro, Functional builders_
* Play-JSON with JAWN parsing
* [Spray](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/spray/src/main/scala/SprayReader.scala)<br/>_Runtime reflection for case-class parsing (but just one time?)_
* Spray with JAWN parsing
* [Json4S](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/json4s/src/main/scala/Json4sReader.scala)<br/>_Runtime reflection for case-class parsing?_
* JSON4S with Jackson parsing
* Json4S with JAWN parsing
* [Circe](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/circe/src/main/scala/CirceReader.scala) with JAWN parsing<br/>_Fork of Argonaut, replacing Scalaz by Cats and macro by shapeless (compile-time derivation). Monadic decoders_
* [Argonaut](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/argonaut/src/main/scala/ArgonautReader.scala) with JAWN parsing<br/>_Based on scalaZ and macro for devirvation. More verbose than Circe. Monadic decoders_

## Results

_lower is better_

> Parse and decode a 600mo file containing around 45000 bid-requests, extracted from real RTB traffic.<br>
> Benchmark run on an AWS c4.2xlarge instance, with `benchs/jmh:run -i 4 -wi 2 -f1 -t1 -bm avgt` (about 25mn)

```
[info] Benchmark                         (readerName)  Mode  Cnt   Score   Error  Units
[info] OneFileReadersbenchmarks.bench      spray-jawn  avgt    4   9.292 ± 0.032   s/op
[info] OneFileReadersbenchmarks.bench           spray  avgt    4  11.692 ± 0.060   s/op
[info] OneFileReadersbenchmarks.bench      circe-jawn  avgt    4  18.034 ± 0.323   s/op
[info] OneFileReadersbenchmarks.bench   argonaut-jawn  avgt    4  20.398 ± 0.201   s/op
[info] OneFileReadersbenchmarks.bench       play-jawn  avgt    4  24.040 ± 0.154   s/op
[info] OneFileReadersbenchmarks.bench            play  avgt    4  28.066 ± 0.126   s/op
[info] OneFileReadersbenchmarks.bench     json4s-jawn  avgt    4  40.180 ± 0.322   s/op
[info] OneFileReadersbenchmarks.bench  json4s-jackson  avgt    4  43.033 ± 0.422   s/op
[info] OneFileReadersbenchmarks.bench          json4s  avgt    4  45.548 ± 0.416   s/op
```

## Why JAWN?

Because it has demonstrated is powerfull in a previous benchmark -better than Jackson in this use case- (based on [that](https://github.com/non/jawn/tree/master/benchmark/src/main/scala/jawn)). And it offers an excellent compability with spray, play, argonaut, json4s...

```
600mo soit 457000 bidrequests à convertir de String -> AST JSON (sur 3 itérations), en mode mono-thread

Jawn parser vers Json4s AST              5162,167 ±  2632,244  ms/op
Jawn parser                              5225,790 ±   773,930  ms/op
Jackson parser                           5413,639 ± 10426,827  ms/op
Jawn parser vers Play AST                6715,119 ±    21,964  ms/op
Jackson parser vers Json4s AST           6921,634 ±  2593,722  ms/op
Jawn parser vers rojomaV3 AST            8194,332 ±  5801,958  ms/op
rojomaV3 parser                          8454,855 ±  5527,186  ms/op
Json4s native parser                     8472,026 ±  1119,310  ms/op
Jawn parser vers Spray AST               8539,325 ± 13691,695  ms/op
Gson parser                              8694,811 ±  6496,280  ms/op
Jackson parser vers Play AST             9472,178 ± 31644,102  ms/op
Jawn parser vers Argonaut AST            9932,325 ± 27138,863  ms/op
Spray parser                            10226,288 ± 11983,117  ms/op
Argonaut parser                         13074,731 ±  9339,648  ms/op
```

## How to run?

```
sbt
$> benchs/jmh:run -i 2 -wi 1 -f1 -t1 -bm avgt
```

The json file used to run this benchmark is too big to be stored on Github. Feel free to ask me the file by DM on twitter ([@julien_lafont](https://www.twitter.com/julien_lafont)).

