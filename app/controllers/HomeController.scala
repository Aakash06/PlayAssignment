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
    ).withSession("user"-> "aakash")
  }

  def action2() = Action{
    implicit request => Ok(views.html.firstVisit())
  }

  def action3() = Action{
    request =>
      val userName = request.session.get("user")

      val (key, value) = userName match {
        case Some(user) => if (user.equalsIgnoreCase("aakash")) {
          ("success", s"Hello Aakash")
        }
        else
        {
          ("error", "No session by this name")
        }
        case None => ("error", "No User/Session Found")
      }

      Redirect(routes.HomeController.action2()).flashing(key -> value)

  }
}
