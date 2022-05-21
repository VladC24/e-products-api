package controllers

import play.api.mvc._
import client.ProductClient
import io.circe.generic.auto._
import io.circe.syntax._
import models.Product
import io.circe.jawn.decode
import play.api.libs.circe.Circe
import repositories.ProductRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ProductController @Inject()(productRepository: ProductRepository, productClient: ProductClient, val controllerComponents: ControllerComponents) extends BaseController with Circe {

  def getList: Action[AnyContent] = Action.async {
    productClient.getProducts.map { product =>
      val productList = product.asJson
      Ok(productList)
    }
  }

  def addProduct = Action.async(circe.json) { implicit request =>
    val jsonRequest = request.body.as[Product]
    jsonRequest match {
      case Left(value) => Future.successful(BadRequest(s"This: $value failed"))
      case Right(value) => productRepository.addProduct(value).map { _ =>
        Ok("Product has been added succesfully!")
      }
    }
  }

  def findProduct(id: Int) = Action.async {implicit request =>
    productRepository.findProductById(id).map { product =>
      val jsonProduct = product.asJson
      Ok(jsonProduct)
    }
  }

}
