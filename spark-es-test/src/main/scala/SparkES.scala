/**
  * Created by lijun on 16/12/10.
  */
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

import org.apache.log4j.Logger
import org.apache.log4j.Level

object SparkES {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("SparkES test").setMaster("local[2]")
	val sc = new SparkContext(conf)
	val sqlContext = new SQLContext(sc)

	//ElasticSearch Configurations and Input Path to the File.
	val jsonPath = "file:///usr/hadoop/lijun/data/meta_Amazon_Instant_Video.json"
	val esConfig = Map(("es.nodes", "se003"), ("es.port", "9200"), ("es.index.auto.create", "true"), ("es.http.timeout", "5m"))

	val jsonDf = sqlContext.read.json(jsonPath)

    //Storing it in Elastic Search Dynamically
	import org.elasticsearch.spark.sql._
	jsonDf.saveToEs("lijun/meta", cfg = esConfig)
  }
}
