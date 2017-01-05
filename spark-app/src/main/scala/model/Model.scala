package model

/**
  * Created by lijun on 16/12/12.
  */
case class AmazonProduct(itemId: String, title: String, url: String, img: String, description: String)
case class AmazonRating(userId: String, productId: String, rating: Double)

case class AmazonProductAndRating(product: AmazonProduct, rating: AmazonRating)
