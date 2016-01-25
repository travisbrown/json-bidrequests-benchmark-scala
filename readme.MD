# Just another JSON scala benchmark

 

> **Warning:** This benchmark is HEAVILY opiniated. It's only focus on the unmarshmalling of RTB bid-requests to a unified case-class representation.

## Candidates:

* [Play-JSON](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/play/src/main/scala/PlayReader.scala#L36). <br/>_Compile-time macro, Functional builders_
* Play-JSON with JAWN parsing
* [Spray](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/spray/src/main/scala/SprayReader.scala).<br/>_Runtime reflection for case-class. Could be optimized, but it will becore extremly verbose!_
* Spray with JAWN parsing
* [Json4S](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/json4s/src/main/scala/Json4sReader.scala).<br/>_?_
* JSON4S with Jackson parsing
* Json4S with JAWN parsing
* [Circe](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/circe/src/main/scala/CirceReader.scala) with JAWN parsing.<br/>_Fork of Argonaut, replacing Scalaz by Cats and macro by shapeless (compile-time derivation). Monadic decoders_
* [Argonaut](https://github.com/studiodev/json-bidrequests-benchmark-scala/blob/master/parsers/argonaut/src/main/scala/ArgonautReader.scala) with JAWN parsing.<br/>_Based on scalaZ and macro for devirvation. More verbose than Circe. Monadic decoders_

## Results

_lower is better_

> read and parse 1go on real bid-requests (mopub extract)

```
[info] OneFileReadersbenchmarks.bench      circe-jawn  avgt    2  20,624           s/op
[info] OneFileReadersbenchmarks.bench   argonaut-jawn  avgt    2  20,819           s/op
[info] OneFileReadersbenchmarks.bench       play-jawn  avgt    2  24,593           s/op
[info] OneFileReadersbenchmarks.bench            play  avgt    2  26,712           s/op
[info] OneFileReadersbenchmarks.bench     json4s-jawn  avgt    2  39,192           s/op
[info] OneFileReadersbenchmarks.bench  json4s-jackson  avgt    2  43,074           s/op
[info] OneFileReadersbenchmarks.bench          json4s  avgt    2  44,704           s/op
[info] OneFileReadersbenchmarks.bench           spray  avgt    2  58,840           s/op
[info] OneFileReadersbenchmarks.bench      spray-jawn  avgt    2  56,005           s/op
```

## How to run ?

```
sbt
$> benchs/jmh:run -i 2 -wi 1 -f1 -t1 -bm avgt
```

