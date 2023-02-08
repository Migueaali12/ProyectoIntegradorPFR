import com.github.tototoshi.csv.CSVReader
import play.api.libs.json._
import java.io.File
import scala.util.{Failure, Success, Try}
import scala.util.matching.Regex

object CrewFix extends App {

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  val crew = data
    .map(row => row("crew"))
    .map(replacePatternUltimate)
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))
    .filter(_.isSuccess)

  println(crew)

  def replacePatternUltimate(original: String) = {
    var txtOr = original
    val pattern1: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
    txtOr = pattern1.replaceAllIn(txtOr, m => m.toString.replace("\"", "-u0022"))
    val pattern2: Regex = "(\\s\"(.*?)\",)".r
    txtOr = pattern2.replaceAllIn(txtOr, m => m.toString.replace("'", "-u0027"))
    val pattern3: Regex = "(:\\s'\"(.*?)',)".r
    txtOr = pattern3.replaceAllIn(txtOr, m => m.toString.replace("\"", "-u0022"))
    txtOr
  }

}
