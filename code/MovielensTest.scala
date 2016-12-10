import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.recommendation.ALS

import org.apache.log4j.Logger
import org.apache.log4j.Level

object MovielensTest {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    val sc = new SparkContext("local[2]", "MovielensTest")
    val rawData = sc.textFile("data/ml-100k/u.data")
    val rawRating = rawData.map(_.split("\t")).take(3)
  }
}
