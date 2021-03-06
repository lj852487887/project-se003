/**
  * Created by lijun on 16/12/10.
  */
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.elasticsearch.spark.sql._

import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating


import org.apache.log4j.Logger
import org.apache.log4j.Level

object SparkAls {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("SparkAls test").setMaster("spark://se003:7077")
	val sc = new SparkContext(conf)
	val sqlContext = new SQLContext(sc)

	val filePath = "/jli/ratings_Musical_Instruments.csv"
	val rawData = sc.textFile(filePath)
	val ratings = rawData.map(_.split(',').take(3) match {
	    case Array(user, item, rate) => 
            Rating(user.toInt, item.toInt, rate.toDouble)
	})
	val model = ALS.train(ratings,50,10,0.01)
	println(model.userFeatures.count)
  }
}
