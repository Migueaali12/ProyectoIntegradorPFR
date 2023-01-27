import com.github.tototoshi.csv.CSVReader
import java.io.File
import play.api.libs.json._
import scala.util.{Failure, Success, Try}
import com.github.tototoshi.csv._
import requests.Response
import scalikejdbc._
object Data2Script extends App {

  val reader = CSVReader.open(new File("movie_dataset.csv"))

  val data = reader.allWithHeaders()
  reader .close()

  case class Movie(id: String,
                   originalLanguage: String,
                   originalTitle: String,
                   budget: Long,
                   popularity: Double,
                   runtime: Double,
                   revenue: Long)

  val movieData = data
    .map(row => Movie(
      row("id"),
      row("original_language"),
      row("original_title"),
      row("budget").toLong,
      row("popularity").toDouble,
      row("runtime") match {
        case valueOfRT if valueOfRT.trim.isEmpty => 0.0
        case valueOfRT => valueOfRT.toDouble
      },
      row("revenue").toLong))

  val SQL_INSERT_PATTERN =
    """INSERT INTO MOVIE ('RAW_ID', 'ORIGINAL_TITLE', 'BUDGET', 'POPULARITY', `RUNTIME', 'REVENUE',)
      |VALUES
      |('%s', '%s', %d, %f, %f, %d, '%s');
      |""".stripMargin

  val scriptData = movieData
    .map(movie => SQL_INSERT_PATTERN.formatLocal(java.util.Locale.US,
      movie.id,
      escapeMysql(movie.originalTitle),
      movie.budget,
      movie.popularity,
      movie.runtime,
      movie.revenue,
      escapeMysql(movie.originalLanguage)
    ))

  def escapeMysql(text: String) : String = text
    .replaceAll("\\\\", "\\\\\\\\")
    .replaceAll("\b", "\\\\b")
    .replaceAll("\n", "\\\\n")
    .replaceAll("\r", "\\\\r")
    .replaceAll("\t", "\\\\t")
    .replaceAll("\\x1A", "\\\\Z")
    .replaceAll("\\x00", "\\\\0")
    .replaceAll("'", "\\\\'")
    .replaceAll("\"", "\\\\\"")
}
