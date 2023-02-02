import com.github.tototoshi.csv.CSVReader
import scalikejdbc._
import play.api.libs.json.{JsArray, Json}
import requests.Response

import java.io.File

object DataBase extends App {

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql//localhost:3306/pintegrador","root","1234")
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

}
