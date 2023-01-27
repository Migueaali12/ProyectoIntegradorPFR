import play.api.libs.json._
import scala.util.{Failure, Success, Try}
import com.github.tototoshi.csv._
import java.io.File
import requests.Response
import scalikejdbc._
object CastReform extends App {

  val reader = CSVReader.open(new File("movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()
/*
  val cast = data
    .map(row => (row("id"), row("cast")))
    .filter(_._2.nonEmpty)
    .map(tuple2 => (tuple2._1, StringContext.processEscapes(tuple2._2)))
    //take(10)
    .map { t2 => (t2._1, actorsNames(t2._2))}
    .map { t2 => (t2._1, Try(Json.parse(t2._2.get))) }
    .filter(_._2.isSuccess)
    .map(t2 => (t2._1, t2._2.get))
    .map(t2 => (t2._1, transform(t2._2)))
    .flatMap(t2 => t2._2.map(name => (t2._1, name)))
    .map(_.productIterator.toList)
    .distinct

 */

  def actorsNames(dataRaw: String): Option[String] = {
    val response: Response = requests
      .post("http://api.meaningcloud.com/topics-2.0",
        data = Map("key" -> "6b5361cd8ea376a35e1387adf350849a",
          "lang" -> "en",
          "txt" -> dataRaw,
          "tt" -> "e"),
        headers = Map("content-type" -> "application/x-www-form-urlencoded"))
    Thread.sleep(500)
    if (response.statusCode == 200) {
      Option(response.text)
    } else
      Option.empty
  }

}
