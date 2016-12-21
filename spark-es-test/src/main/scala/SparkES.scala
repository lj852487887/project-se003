/**
  * Created by lijun on 16/12/10.
  */
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

import org.elasticsearch.spark.sql._

import org.apache.log4j.Logger
import org.apache.log4j.Level

object SparkES {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    //val conf = new SparkConf().setAppName("SparkES test").setMaster("local[2]")
    val conf = new SparkConf().setAppName("SparkES test").setMaster("spark://se003:7077")

	val sc = new SparkContext(conf)
	val sqlContext = new SQLContext(sc)

	//ElasticSearch Configurations and Input Path to the File.
	val reviewPath = "/jli/data/Amazon-video-games-data/reviews_Video_Games.json"
	val metaPath = "/jli/data/Amazon-video-games-data/meta_Video_Games.json"

	val esConfig = Map(("es.nodes", "se003"), ("es.port", "9200"), ("es.index.auto.create", "true"), ("es.http.timeout", "5m"))

	val reviewDf = sqlContext.read.json(reviewPath)
	val metaDf = sqlContext.read.json(metaPath)
    //Storing it in Elastic Search Dynamically
	
	reviewDf.saveToEs("jli_reviews/videogames", cfg = esConfig)
	metaDf.saveToEs("jli_products/videogames", cfg = esConfig)
  }
}
