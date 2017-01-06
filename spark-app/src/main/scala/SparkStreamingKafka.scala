
import _root_.kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.dstream.DStream.toPairDStreamFunctions
import org.apache.spark.streaming.kafka.KafkaUtils
import org.elasticsearch.spark.sql._
import org.elasticsearch.spark.rdd._

object SparkStreamingKafka {

  def main(args: Array[String]): Unit = {


    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)


    // Create a StreamingContext with the given master URL
    val conf = new SparkConf().setMaster("spark://se003:7077").setAppName("SparkStreamingKafka")

    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val ssc = new StreamingContext(sc, Seconds(5))
    ssc.checkpoint("checkpoint")

    // Kafka configurations
    val topics = Set("user_events")
    val brokers = "10.1.2.3:9092,10.1.2.4:9092,10.1.2.5:9092"
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers, "serializer.class" -> "kafka.serializer.StringEncoder")
    val esConfig = Map(("es.nodes", "se003"), ("es.port", "9200"), ("es.index.auto.create", "true"), ("es.http.timeout", "5m"))


    // Create a direct stream
    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    val events = kafkaStream.map(_._2)
    // Compute user click times
    events.foreachRDD{
      rdd =>
        if (!rdd.isEmpty()) {
          EsSpark.saveToEs(rdd,"/kafka_logs/test",esConfig)
        }
    }

    ssc.start()
    ssc.awaitTermination()

  }
}
