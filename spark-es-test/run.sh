#!/bin/bash
echo "compile myFirstScalaApp"
sbt clean package
echo "run wordcount on the cluster"

/opt/spark/bin/spark-submit --class SparkES ./target/scala-2.10/sparkes-test_2.10-1.0.jar




