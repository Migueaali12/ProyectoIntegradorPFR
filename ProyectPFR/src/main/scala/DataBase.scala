import com.github.tototoshi.csv.CSVReader
import scalikejdbc._
import play.api.libs.json.{JsArray, Json}
import scala.util.{Failure, Success, Try}
import requests.Response
import java.io.File

object DataBase extends App {

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/taller","root","Rootsql")
  implicit val session: DBSession = AutoSession

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

  val cast = data
    .map(row => row("cast"))
    .filter(_.nonEmpty)
    .map(StringContext.processEscapes)
    .take(10)
    .map(actorsNames)
    .map(json => Try(Json.parse(json.get)))
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(json => json("entity_list").as[JsArray].value)
    .map(_ ("form"))
    .map(data => data.as[String])
    .distinct

/*
  cast.foreach(x =>sql"""
    INSERT INTO elenco
    VALUES
    (${x})
      """
    .stripMargin
    .update
    .apply()
  )

 */

  val entities = sql"SELECT * FROM elenco WHERE(Nombres = 'Daniel Craig') "
    .map(_.toMap())
    .list.apply()

  entities.foreach(x => println(x))

}
