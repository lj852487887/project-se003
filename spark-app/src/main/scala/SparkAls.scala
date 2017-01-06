
/**
  * Created by lijun on 17/1/6.
  */

import util.Dictionary
import model.AmazonRating
import org.apache.log4j.{Level, Logger}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating, _}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.Map


object SparkAls {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("AlsRecommender").setMaster("spark://se003:7077")
    val sc = new SparkContext(conf)
    //val path = "/jli/data/Amazon-all-data/ratings.csv"
    val path = "/jli/data/Amazon-video-games-data/ratings_Video_Games.csv"

    //获取RDD
    val rawTrainingRatings = sc.textFile(path).map {
      line =>
        val Array(userId, productId, scoreStr) = line.split(",").take(3)
        AmazonRating(userId, productId, scoreStr.toDouble)
    }

    val trainingRatings = rawTrainingRatings.groupBy(_.userId)
      .flatMap(_._2)
      .cache()

    println(s"Parsed ratingFile. Kept ${trainingRatings.count()} ratings out of ${rawTrainingRatings.count()}")

    val userDict = new Dictionary(trainingRatings.map(_.userId).distinct.collect)
    println("User Dictionary have " + userDict.size + " elements.")
    val productDict = new Dictionary(trainingRatings.map(_.productId).distinct.collect)
    println("Product Dictionary have " + productDict.size + " elements.")

    def toSparkRating(amazonRating: AmazonRating) = {
      Rating(userDict.getIndex(amazonRating.userId),
        productDict.getIndex(amazonRating.productId),
        amazonRating.rating)
    }

    def toAmazonRating(rating: Rating) = {
      AmazonRating(userDict.getWord(rating.user),
        productDict.getWord(rating.product),
        rating.rating
      )
    }

    val sparkRatings = trainingRatings.map(toSparkRating)

    println(sparkRatings.take(10))
    //准备数据
    println("准备完数据")

    //model(sc, rawUserMoviesData, rawHotMoviesData)

    //evaluate(sc,rawUserMoviesData, rawHotMoviesData)

    //recommend(sc, rawUserMoviesData, rawHotMoviesData,base)
  }





}


