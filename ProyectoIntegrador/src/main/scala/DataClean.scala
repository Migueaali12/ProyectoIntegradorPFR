import play.api.libs.json._
import scala.util.{Failure, Success, Try}
import scala.util.matching.Regex
import com.github.tototoshi.csv._
import java.io.File

object DataClean extends App {

  val reader = CSVReader.open(new File("movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  /*
  def replacePattern(original: String): String = {
    var txtOr = original
    val pattern: Regex = "(\\s\"(.*?)\",)".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("'", "-u0027")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  def replacePattern2(original: String): String = {
    var txtOr = original
    val pattern: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  def replacePattern3(original: String): String = {
    var txtOr = original
    val pattern: Regex = "(:\\s'\"(.*?)',)".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

   */

  val crew = data
    .map(row => row("crew"))
    .map(replacePatternUltimate)
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))
    .filter(_.isSuccess)

  def replacePatternUltimate(original: String): String = {
    var txtOr = original

    val pattern2: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
    txtOr = pattern2.replaceAllIn(txtOr, m => m.matched.replace("\"", "-u0022"))

    val pattern1: Regex = "(\\s\"(.*?)\",)".r
    txtOr = pattern1.replaceAllIn(txtOr, m => m.matched.replace("'", "-u0027"))

    val pattern3: Regex = "(:\\s'\"(.*?)',)".r
    txtOr = pattern3.replaceAllIn(txtOr, m => m.matched.replace("\"", "-u0022"))

    txtOr
  }

  val idNameList = crew
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(_.as[JsArray].value)
    .map(_.as[JsObject])
    .map(jsObj => (jsObj("id").as[Int],jsObj("name").as[String]))
    .groupBy(identity)
    .map {
      case (tupla, list) => (tupla, list.size)
    }
    .filter(_._2 > 1)
    .toList
    .sortBy(_._2)
    .reverse


}
