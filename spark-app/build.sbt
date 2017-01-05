name := "SparkApp "

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-mllib_2.10" % "1.6.0",
  "org.apache.spark" %% "spark-sql_2.10" % "1.6.0",
  "org.apache.spark" %% "spark-streaming_2.10" % "1.6.0",
  "org.elasticsearch" % "elasticsearch-spark_2.10" % "2.2.0"
)