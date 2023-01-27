import play.api.libs.json._
import com.github.tototoshi.csv._
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import play.api.libs.json._
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import java.io.File
object ParcialPF extends App {

  implicit val theme = DefaultTheme.copy(
    elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45)
  )

  val reader = CSVReader.open(new File("src/data_parcial_2_OO.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  val tournaments = data.flatMap(elem => elem.get("tourney_id")).distinct.size
  /*
  val tournaments1 = data.flatMap(elem => elem.get("tourney_name")).groupBy(x => x.split(":")(0))
    . map {
      case (nombre, lista) => (nombre, lista.size)
    }.toList.sortBy(_._2).reverse
  println(players_Info)

   */
  val tournamentName = data.flatMap(elem => elem.get("tourney_name")).distinct

  //Análisis Exploratorios
  //Numericos

  val hand = data
    .flatMap(row => row.get("players_info"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "hand")
    .map(jsValue => jsValue.as[String]).filter(x => x != "null")

  val handDis = hand
    .groupBy {
      case genre => genre
    }.map {
    case x => (x._1, x._2.size)
  }

  printf("Las manos son: %s\n" +
    "La mano menos utilizado es: %s con: %s de apariciones\n" +
    "La mano mas utilizado es: %s con: %s de apariciones\n" +
    "%s\n",
    handDis.map(_._1).toSet, handDis.minBy(_._2)._1, handDis.minBy(_._2)._2, handDis.maxBy(_._2)._1,
    handDis.maxBy(_._2)._2, handDis.toList.sortBy(_._2))

  val height = data
    .flatMap(row => row.get("players_info"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "height")
    .map(jsValue => jsValue.asOpt[Int].getOrElse(0)) //devuelve 0 si es null

  val min0height = height.min
  val minheight = height.filter(_ > 0).min
  val promheight = height.sum.toDouble / height.size
  val maxheight = height.max

  printf("\nEl promedio de height es: %s\n" +
    "El valor minimo de height contando 0 es: %s\n" +
    "El valor minimo de height exceptuando 0 es: %s\n" +
    "El valor maximo de height es %s\n",
    promheight, min0height, minheight, maxheight)

  val best_of = data
    .flatMap(row => row.get("match_info"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "best_of")
    .map(jsValue => jsValue.asOpt[Int].getOrElse(0))

  val min0best = best_of.min
  val minbest = best_of.filter(_ > 0).min
  val prombest = best_of.sum.toDouble / best_of.size
  val maxbest = best_of.max

  printf("\nEl promedio de height es: %s\n" +
    "El valor minimo de height contando 0 es: %s\n" +
    "El valor minimo de height exceptuando 0 es: %s\n" +
    "El valor maximo de height es %s\n",
    prombest, min0best, minbest, maxbest)

  val minutes = data
    .flatMap(row => row.get("match_info"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "minutes")
    .map(jsValue => jsValue.asOpt[Int].getOrElse(0))

  val min0Min = minutes.min
  val minMin = minutes.filter(_ > 0).min
  val promMin = minutes.sum.toDouble / minutes.size
  val maxMin = minutes.max

  printf("\nEl promedio de height es: %s\n" +
    "El valor minimo de height contando 0 es: %s\n" +
    "El valor minimo de height exceptuando 0 es: %s\n" +
    "El valor maximo de height es %s\n",
    promMin, min0Min, minMin, maxMin)

  val histoheight = height.map(_.toDouble)

  Histogram(histoheight)
    .title("Tamaño")
    .xAxis()
    .yAxis()
    .xbounds(histoheight.min, histoheight.max)
    .render()
    .write(new File("C:\\Users\\Miguel Alvarez\\CodeStats\\histogramHeight.png"))

  val histobest = best_of.map(_.toDouble)

  Histogram(histobest)
    .title("Numero de Sets a jugar")
    .xAxis()
    .yAxis()
    .xbounds(histobest.min, histobest.max)
    .render()
    .write(new File("C:\\Users\\Miguel Alvarez\\CodeStats\\histogrambestOf.png"))

  val histoMinutes = minutes.map(_.toDouble)

  Histogram(histoMinutes)
    .title("Minutos que duró el Partido")
    .xAxis()
    .yAxis()
    .xbounds(histoMinutes.min, histoMinutes.max)
    .render()
    .write(new File("C:\\Users\\Miguel Alvarez\\CodeStats\\histogramMinutes.png"))

  val handDis2 = hand
    .groupBy {
      case genre => genre
    }.map {
    case x => (x._1, x._2.size)
  }.toList
    .sortBy(_._2)
    .reverse

  BarChart(handDis2.map(_._2).map(_.toDouble))
    .title("Manos")
    .xAxis(handDis2.map(_._1))
    .yAxis()
    .frame()
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\Miguel Alvarez\\CodeStats\\BarchartHand.png"))

  //Pregunta: COMO SE LLAMABAN LOS JUGADORES QUE PARTICIPARON EN EL
  //TORNEO  "Roland Garros "Y QUE EDAD TENIAN?

  val tourney_name = data.flatMap(elem => elem.get("tourney_name"))

  val players_name = data
    .flatMap(row => row.get("players_info"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "name")
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map {
    case x => (x._1, x._2.size)
  }.toList.max

  println(players_name)

  val players_age = data
    .flatMap(row => row.get("players_info"))
    .map(row => Json.parse(row))
    .flatMap(JsonData => JsonData \\ "age")
    .map(jsValue => jsValue.asOpt[Double].getOrElse(0.0))

  val tuple = (tourney_name, players_name, players_age)

  /*val filteredTuple = tuple match {
    case tupla if tupla._1.equals("Roland Garros") => (tupla._2, tupla._3)
  }

   */

 










}
