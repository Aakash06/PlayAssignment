package controllers

import java.util
import javax.inject._

import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController extends Controller {

  /**
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  /*def action1(id : Long,name : String) = Action{
    Result(
      header = ResponseHeader(200,Map(CONTENT_TYPE -> "text/plain")),
      body = HttpEntity.Strict.apply(ByteString(s"$id Hello World $name"),Some("text/plain"))
    )
  }
*/
  def index() = Action{
    Result(
      header = ResponseHeader(200,Map(CONTENT_TYPE -> "text/plain")),
      body = HttpEntity.Strict.apply(ByteString(s"Hello Everyone"),Some("text/plain"))
    ).withSession("user"-> "Session Started")
  }

  def action2()= Action{ implicit request: Request[AnyContent] =>
          request.session.get("user").map(value=>
            Ok(request.flash.get("success").getOrElse("No user found "))
          ).getOrElse(Unauthorized("Oops : No user"))
  }

  def action3(user: String) = Action{
    Redirect(routes.HomeController.action2()).flashing(
      "success" -> s"There is $user named users")
  }

}
