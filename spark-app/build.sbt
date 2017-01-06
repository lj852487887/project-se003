name := "SparkApp "

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.6.0",
  "org.apache.spark" %% "spark-sql" % "1.6.0",
  "org.apache.spark" %% "spark-streaming" % "1.6.0",
  "org.elasticsearch" % "elasticsearch-spark_2.10" % "2.2.0",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0",
  "org.codehaus.jettison" % "jettison" % "1.3.7",
  "com.alibaba" % "fastjson" % "1.1.26",
  "org.apache.kafka" %% "kafka"  % "0.8.2.1"
)
