import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot.aesthetics.Theme
import play.api.libs.json.Json
import scala.math._

object CStats extends App {

  implicit val theme: Theme = DefaultTheme.copy(
    elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45)
  )

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val releaseDateList = data.map(row => row("release_date"))
    .filter(!_.equals("")).map(text => LocalDate.parse(text, dateFormatter))

  val yearReleaseList = releaseDateList.map(_.getYear).map(_.toDouble)

  Histogram(yearReleaseList)
    .title("release_date")
    .xAxis()
    .yAxis()
    .xbounds(yearReleaseList.min, yearReleaseList.max)
    .render()
    .write(new File("CodeStats\\histoYearReleaseDate.png"))

  val productionCompanies = data
    .flatMap(row => row.get("production_companies"))
    .map(row => Json.parse(row))
    .flatMap(jsonData => jsonData \\ "name")
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  BarChart(productionCompanies.take(10).map(_._2).map(_.toDouble))
    .title("Compañias Productoras")
    .xAxis(productionCompanies.take(10).map(_._1))
    .yAxis()
    .frame()
    .yLabel("Productions")
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartProductionCompanies.png"))

  //Datos numericos

  val budget = data.map(x => (x("original_title"), x("budget").toDouble))

  BarChart(budget.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(x => log(x._2)))
    .title("budget")
    .xAxis(budget.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartBudget.png"))

  val popularity = data.map(x => (x("original_title"), x("popularity").toDouble))

  BarChart(popularity.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(x => log(x._2)))
    .title("popularity")
    .xAxis(popularity.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartPopularity.png"))

  val revenue = data.map(x => (x("original_title"), x("revenue").toDouble))

  BarChart(revenue.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._2))
    .title("revenue")
    .xAxis(revenue.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartRevenue.png"))

  val runtime = data.map(x => (x("original_title"), x("runtime")))
  val filteredRuntime = runtime.filter(_._2.nonEmpty).map(x => (x._1, x._2.toDouble))

  BarChart(filteredRuntime.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._2))
    .title("runtime")
    .xAxis(filteredRuntime.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartRuntime.png"))

  val vote_average = data.map(x => (x("original_title"), x("vote_average").toDouble))

  BarChart(vote_average.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._2))
    .title("vote_average")
    .xAxis(vote_average.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartVote_avrg.png"))

  val vote_count = data.map(x => (x("original_title"), x("vote_count").toDouble))

  BarChart(vote_count.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._2))
    .title("vote_count")
    .xAxis(vote_count.sortWith((x1, x2) => x1._2 > x2._2).take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartVoteCount.png"))

  //Datos tipo cadena

  val genres = data.flatMap(x => x.get("genres"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  BarChart(genres.take(10).map(_._2).map(_.toDouble))
    .title("genres")
    .xAxis(genres.take(10).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartGenres.png"))

  val release_date = data.flatMap(x => x.get("release_date"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, log(lista.size)) }
    .toList
    .sortBy(_._2)
    .reverse

  BarChart(release_date.take(7).map(_._2).map(_.toDouble))
    .title("release_date")
    .xAxis(release_date.take(7).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartRelease_date.png"))

  val status = data.flatMap(x => x.get("status"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, log(lista.size)) }
    .toList
    .sortBy(_._2)
    .reverse

  BarChart(status.map(_._2).map(_.toDouble))
    .title("status")
    .xAxis(status.map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarchartStatus.png"))

  val title = data.flatMap(x => x.get("title"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size.toDouble) }
    .toSeq
    .sortBy(_._2)
    .reverse

  PieChart(title.take(3))
    .title("title")
    .rightLegend()
    .render()
    .write(new File("CodeStats\\PieChartTitle.png"))

  val director = data.flatMap(x => x.get("director"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, log(lista.size)) }
    .filter(x => x._1.nonEmpty)
    .toList
    .sortBy(_._2)
    .reverse

  BarChart(director.take(8).map(_._2))
    .title("director")
    .xAxis(director.take(8).map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("CodeStats\\BarcharDirector.png"))

  val spoken_languaje = data
    .flatMap(row => row.get("spoken_languages"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "name")
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, log(lista.size)) }
    .toSeq
    .sortBy(_._2)
    .reverse

  PieChart(spoken_languaje.take(5))
    .title("spoken_languaje")
    .rightLegend()
    .render()
    .write(new File("CodeStats\\PieChartSpokenLanguage.png"))



}
