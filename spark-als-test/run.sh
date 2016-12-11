#!/bin/bash
echo "compile spark-als test"
sbt clean package
echo "run spark-als test local"

/opt/spark/bin/spark-submit --class SparkAls --jars ./elasticsearch-spark_2.10-2.2.0.jar ./target/scala-2.10/sparkes-test_2.10-1.0.jar




