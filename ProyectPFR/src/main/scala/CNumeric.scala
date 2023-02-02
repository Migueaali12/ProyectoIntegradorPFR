import com.github.tototoshi.csv._
import java.io.File
object CNumeric extends App {

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //budget
  val budget = data.flatMap(elem => elem.get("budget")).map(_.toInt)
  val min0Budget = budget.min
  val minBudget = budget.filter(_ > 0).min
  val PromBudget = budget.sum.toDouble / budget.size
  val maxBudget = budget.max

  printf("\nEl promedio de budget es: %s\n" +
    "El valor minimo de budget contando 0 es: %s\n" +
    "El valor minimo de budget exceptuando 0 es: %s\n" +
    "El valor maximo de budget es %s\n",
    PromBudget, min0Budget, minBudget, maxBudget)

  //popularity
  val popularity = data.flatMap(elem => elem.get("popularity")).map(_.toDouble)
  val min0Pop = popularity.min
  val minPop = popularity.filter(_ > 0).min
  val PromPop = popularity.sum / popularity.size
  val maxPop = popularity.max

  printf("\nEl promedio de popularity es: %s\n" +
    "El valor minimo de popularity contando 0 es: %s\n" +
    "El valor minimo de popularity exceptuando 0 es: %s\n" +
    "El valor maximo de popularity es %s\n",
    PromPop, min0Pop, minPop, maxPop)

  //revenue
  val revenue = data.flatMap(elem => elem.get("revenue")).map(_.toDouble)
  val min0Revenue = revenue.min
  val minRevenue = revenue.filter(_ > 0).min
  val PromRevenue = revenue.sum / revenue.size
  val maxRevenue = revenue.max

  printf("\nEl promedio de revenue es: %s\n" +
    "El valor minimo de revenue contando 0 es: %s\n" +
    "El valor minimo de revenue exceptuando 0 es: %s\n" +
    "El valor maximo de revenue es %s\n",
    PromRevenue, min0Revenue, minRevenue, maxRevenue)


  //runtime
  var runtime = data.flatMap(elem => elem.get("runtime")).filter(_.isEmpty != true)
  val runtime0 = runtime.filter(_.isEmpty == true).map(_ => "0")
  runtime = runtime ++ runtime0
  val runtime1 = runtime.map(_.toDouble)

  val min0Runtime = runtime1.min
  val minRuntime = runtime1.filter(_ > 0).min
  val PromRuntime = runtime1.sum / runtime.size
  val maxRuntime = runtime1.max

  printf("\nEl promedio de runtime es: %s\n" +
    "El valor minimo de runtime contando 0 es: %s\n" +
    "El valor minimo de runtime exceptuando 0 es: %s\n" +
    "El valor maximo de runtime es %s\n",
    PromRuntime, min0Runtime, minRuntime, maxRuntime)

  //vote_average
  val vote_average = data.flatMap(elem => elem.get("vote_average")).map(_.toDouble)
  val min0Va = vote_average.min
  val minVa = vote_average.filter(_ > 0).min
  val PromVa = vote_average.sum / vote_average.size
  val maxVa = vote_average.max

  printf("\nEl promedio de vote_average es: %s\n" +
    "El valor minimo de vote_average contando 0 es: %s\n" +
    "El valor minimo de vote_average exceptuando 0 es: %s\n" +
    "El valor maximo de vote_average es %s\n",
    PromVa, min0Va, minVa, maxVa)

  //vote_count
  val vote_count = data.flatMap(elem => elem.get("vote_count")).map(_.toInt)
  val min0Vc = vote_count.min
  val minVc = vote_count.filter(_ > 0).min
  val PromVc = vote_count.sum.toDouble / vote_count.size
  val maxVc = vote_count.max

  printf("\nEl promedio de vote_count es: %s\n" +
    "El valor minimo de vote_count contando 0 es: %s\n" +
    "El valor minimo de vote_count exceptuando 0 es: %s\n" +
    "El valor maximo de vote_count es %s\n",
    PromVc, min0Vc, minVc, maxVc)

}
