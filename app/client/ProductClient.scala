package client

import io.circe.generic.auto._
import io.circe.jawn.decode
import play.api.libs.ws.WSClient
import models.Product

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

class ProductClient @Inject()(ws: WSClient) {

  def getProducts: Future[List[Product]] = {
    val url = "https://fakestoreapi.com/products"
    val request = ws.url(url)
    val response = request.get()
    val newProduct = response.map { x =>
      val result = x.body
      decode[List[Product]](result).getOrElse(List.empty)
    }
    newProduct
  }

}
