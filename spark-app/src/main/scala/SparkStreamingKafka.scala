package SparkApp

import _root_.kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.dstream.DStream.toPairDStreamFunctions
import org.apache.spark.streaming.kafka.KafkaUtils
import com.alibaba.fastjson.JSON

object SparkStreamingKafka {

  def main(args: Array[String]): Unit = {


    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)


    // Create a StreamingContext with the given master URL
    val conf = new SparkConf().setMaster("spark://se003:7077").setAppName("SparkStreamingKafka")
    val ssc = new StreamingContext(conf, Seconds(5))

    // Kafka configurations
    val topics = Set("user_events")
    val brokers = "10.1.2.3:9092,10.1.2.4:9092,10.1.2.5:9092"
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers, "serializer.class" -> "kafka.serializer.StringEncoder")


    // Create a direct stream
    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

    val events = kafkaStream.flatMap(line => {
      val data = JSON.parseObject(line._2)
      Some(data)
    })

    // Compute user click times
    val userClicks = events.print()

    ssc.start()
    ssc.awaitTermination()

  }
}