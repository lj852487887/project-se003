

import org.apache.log4j.{Level, Logger}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating, _}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.Map

case class AmazonRating(userID: String, movieID: Int, rating: Double) extends scala.Serializable

object SparkAls {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("AlsRecommender").setMaster("spark://se003:7077")
    val sc = new SparkContext(conf)
    val path = "/jli/data/Amazon-all-data/ratings.csv"

    //获取RDD
    val rawUserRatingData = sc.textFile(path)

    //准备数据
    preparation(rawUserRatingData)
    println("准备完数据")

    //model(sc, rawUserMoviesData, rawHotMoviesData)

    //evaluate(sc,rawUserMoviesData, rawHotMoviesData)

    //recommend(sc, rawUserMoviesData, rawHotMoviesData,base)
  }

  //分析清理数据
  def preparation( rawUserRatingData: RDD[String]) = {
    val userIDStats = rawUserRatingData.map(_.split(',')(0).trim).distinct().zipWithUniqueId().map(_._2.toDouble).stats()
    val itemIDStats = rawUserRatingData.map(_.split(',')(1).trim.toDouble).distinct().stats()
    println(userIDStats)
    println(itemIDStats)
    
  }



}


