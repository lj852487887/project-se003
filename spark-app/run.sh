#!/bin/bash
echo "run spark-app on cluster"
/opt/spark/bin/spark-submit --class SparkStreamingKafka --jars ./lib/spark-streaming-kafka_2.10-1.6.0.jar,./lib/kafka_2.10-0.8.2.1.jar,./lib/metrics-core-2.2.0.jar,./lib/zkclient-0.3.jar,./lib/elasticsearch-spark_2.10-2.2.0.jar  ./target/scala-2.10/sparkapp-_2.10-1.0.jar




