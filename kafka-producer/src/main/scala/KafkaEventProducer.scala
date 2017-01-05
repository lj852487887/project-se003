
import java.util.Properties
import scala.util.Properties
import org.codehaus.jettison.json.JSONObject
import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig
import scala.util.Random

object KafkaEventProducer {
 
  private val users = Array(
      "Jack", "Tom",
      "Hank", "Jimmy",
      "John", "Smith",
      "Kevin", "Peter",
      "Lucy", "Nancy")
     
  private val random = new Random()
     
  private var pointer = -1
 
  def getUserID() : String = {
       pointer = pointer + 1
    if(pointer >= users.length) {
      pointer = 0
      users(pointer)
    } else {
      users(pointer)
    }
  }
 
  def click() : Double = {
    random.nextInt(10)
  }
 
  // bin/kafka-topics.sh --zookeeper 10.1.2.3:2181,10.1.2.4:2181,10.1.2.5:2181/kafka --create --topic user_events --replication-factor 2 --partitions 2
  // bin/kafka-topics.sh --zookeeper 10.1.2.3:2181,10.1.2.4:2181,10.1.2.5:2181/kafka --list
  // bin/kafka-topics.sh --zookeeper 10.1.2.3:2181,10.1.2.4:2181,10.1.2.5:2181/kafka --describe user_events
  // bin/kafka-console-consumer.sh --zookeeper 10.1.2.3:2181,10.1.2.4:2181,10.1.2.5:22181/kafka --topic user_events --from-beginning
  def main(args: Array[String]): Unit = {
    val topic = "user_events"
    val brokers = "10.1.2.3:9092,10.1.2.4:9092,10.1.2.5:9092"
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")
   
    val kafkaConfig = new ProducerConfig(props)
    val producer = new Producer[String, String](kafkaConfig)
   
    while(true) {
      // prepare event data
      val event = new JSONObject()
      event
        .put("uid", getUserID)
        .put("event_time", System.currentTimeMillis.toString)
        .put("os_type", "Android")
        .put("click_count", click)
     
      // produce event message
      producer.send(new KeyedMessage[String, String](topic, event.toString))
      println("Message sent: " + event)
     
      Thread.sleep(200)
    }
  }  
}