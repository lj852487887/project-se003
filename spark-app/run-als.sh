#!/bin/bash
echo "run spark-app on cluster"
/opt/spark/bin/spark-submit --class SparkAls ./target/scala-2.10/sparkapp-_2.10-1.0.jar

/opt/spark/bin/spark-submit --class SparkAls --num-executors 30 --executor-memory 1G --driver-memory 1G --conf spark.default.parallelism=1000 --conf spark.storage.memoryFraction=0.5 --conf spark.shuffle.memoryFraction=0.3 ./target/scala-2.10/sparkapp-_2.10-1.0.jar \

/opt/spark/bin/spark-shell --master spark://se003:7077
