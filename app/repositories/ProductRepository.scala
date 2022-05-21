package repositories

import demo.Tables
import models.{Product, Rating}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepository @Inject()(@NamedDatabase(value="e_products") protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  def findProductById(id : Int): Future[Product] = {
    val query = Tables.Products.filter(productRow => productRow.id === id.toLong)
    val queryResult = db.run(query.result)
    queryResult.map(result => result.head).map(product => Product(id = product.id, title = product.title, price = product.price, description = product.description, category = product.category, image = product.image, rating = Rating(rate = product.ratingRate, count = product.ratingCount.toInt)))

    // TODO: Return an Option[Product] so it doesn't error when trying to retrieve an error that does not exist in the db - return a 404
  }

  def addProduct(product: Product): Future[Int] = {
    val query = Tables.Products.map(product => (product.id, product.price, product.image, product.title, product.category, product.description, product.ratingCount, product.ratingRate)) += (product.id, product.price.toLong, product.image, product.title, product.category, product.description, product.rating.count, product.rating.rate.toLong)
    val queryResult = db.run(query)
    queryResult
  }
}

