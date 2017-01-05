/**
  * Created by lijun on 16/12/10.
  */
import org.apache.spark._
import org.apache.spark.streaming._


import org.apache.log4j.Logger
import org.apache.log4j.Level

object SparkStreamingTest {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    //val conf = new SparkConf().setAppName("SparkES test").setMaster("local[2]")
    val conf = new SparkConf().setAppName("SparkStreamingTest").setMaster("spark://se003:7077")
    val ssc = new StreamingContext(conf, Seconds(5))

    val ds = ssc.socketTextStream("10.1.2.3", 9999)
    val result = ds.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()

	  ssc.start()             
	  ssc.awaitTermination() 
  }
}