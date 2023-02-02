import com.github.tototoshi.csv._
import java.io.File
object CNumeric extends App {

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //budget
  val budget = data.flatMap(elem => elem.get("budget")).map(_.toInt)

  printf("\nEl promedio de budget es: %s\n" +
    "El valor minimo de budget contando 0 es: %s\n" +
    "El valor minimo de budget exceptuando 0 es: %s\n" +
    "El valor maximo de budget es %s\n",
    budget.sum.toDouble / budget.size, budget.min, budget.filter(_ > 0).min, budget.max)

  //popularity
  val popularity = data.flatMap(elem => elem.get("popularity")).map(_.toDouble)

  printf("\nEl promedio de popularity es: %s\n" +
    "El valor minimo de popularity contando 0 es: %s\n" +
    "El valor minimo de popularity exceptuando 0 es: %s\n" +
    "El valor maximo de popularity es %s\n",
    popularity.sum / popularity.size, popularity.min, popularity.filter(_ > 0).min, popularity.max)

  //revenue
  val revenue = data.flatMap(elem => elem.get("revenue")).map(_.toDouble)

  printf("\nEl promedio de revenue es: %s\n" +
    "El valor minimo de revenue contando 0 es: %s\n" +
    "El valor minimo de revenue exceptuando 0 es: %s\n" +
    "El valor maximo de revenue es %s\n",
    revenue.sum / revenue.size, revenue.min, revenue.filter(_ > 0).min, revenue.max)

  //runtime
  var runtime = data.flatMap(elem => elem.get("runtime")).filter(_.isEmpty != true)
  val runtime0 = runtime.filter(_.isEmpty == true).map(_ => "0")
  runtime = runtime ++ runtime0
  val runtime1 = runtime.map(_.toDouble)

  printf("\nEl promedio de runtime es: %s\n" +
    "El valor minimo de runtime contando 0 es: %s\n" +
    "El valor minimo de runtime exceptuando 0 es: %s\n" +
    "El valor maximo de runtime es %s\n",
    runtime1.sum / runtime.size, runtime1.min, runtime1.filter(_ > 0).min, runtime1.max)

  //vote_average
  val vote_average = data.flatMap(elem => elem.get("vote_average")).map(_.toDouble)

  printf("\nEl promedio de vote_average es: %s\n" +
    "El valor minimo de vote_average contando 0 es: %s\n" +
    "El valor minimo de vote_average exceptuando 0 es: %s\n" +
    "El valor maximo de vote_average es %s\n",
    vote_average.sum / vote_average.size, vote_average.min, vote_average.filter(_ > 0).min, vote_average.max)

  //vote_count
  val vote_count = data.flatMap(elem => elem.get("vote_count")).map(_.toInt)

  printf("\nEl promedio de vote_count es: %s\n" +
    "El valor minimo de vote_count contando 0 es: %s\n" +
    "El valor minimo de vote_count exceptuando 0 es: %s\n" +
    "El valor maximo de vote_count es %s\n",
    vote_count.sum.toDouble / vote_count.size, vote_count.min, vote_count.filter(_ > 0).min, vote_count.max)

}
