#!/bin/bash
echo "run spark-app on cluster"
/opt/spark/bin/spark-submit --class SparkAls ./target/scala-2.10/sparkapp-_2.10-1.0.jar




