package controllers

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

  def index(user: String) = Action {
    Result(
      header = ResponseHeader(200, Map(CONTENT_TYPE -> "text/plain")),
      body = HttpEntity.Strict.apply(ByteString(s"Hello Everyone "), Some("text/plain"))
    ).withSession("user" -> s"$user")
  }

  def check(users: String) = Action { implicit request: Request[AnyContent] =>

    val userName = request.session.get("user")

    val (key ,message) = userName match {
      case Some(user) if user.equalsIgnoreCase("users") =>
        ("Success","Welcome")
      case None => ("Error","Sorry No  user exist by this name ")
    }

    Redirect(routes.HomeController.action()).flashing(key->message)
  }

  def action() = Action { implicit request =>
//println("scscdsndn")
    Ok(views.html.welcome())
  }
}
