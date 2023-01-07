import com.github.tototoshi.csv._
import java.io.File

object ACnumericas extends App {

  val reader = CSVReader.open(new File("movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //index
  val index = data.flatMap(elem => elem.get("index")).map(_.toInt)
  val minIndex = index.filter(_ > 0).min
  val PromIndex = index.sum.toDouble / index.size
  val maxIndex = index.max

  printf("El promedio de index es: %s\n" +
    "El valor minimo de index es: %s\n" +
    "El valor maximo de index es %s\n",
    PromIndex,minIndex, maxIndex)

  //budget
  val budget = data.flatMap(elem => elem.get("budget")).map(_.toInt)
  val minBudget = budget.filter(_ > 0).min
  val PromBudget = budget.sum.toDouble / budget.size
  val maxBudget = budget.max

  printf("\nEl promedio de budget es: %s\n" +
    "El valor minimo de budget es: %s\n" +
    "El valor maximo de budget es %s\n",
    PromBudget, minBudget, maxBudget)

  //id
  val id = data.flatMap(elem => elem.get("id")).map(_.toInt)
  val minId = id.filter(_ > 0).min
  val PromId = id.sum.toDouble / id.size
  val maxId = id.max

  printf("\nEl promedio de id es: %s\n" +
    "El valor minimo de id es: %s\n" +
    "El valor maximo de id es %s\n",
    PromId, minId, maxId)

  //popularity
  val popularity = data.flatMap(elem => elem.get("popularity")).map(_.toDouble)
  val minPop = popularity.filter(_ > 0).min
  val PromPop = popularity.sum / popularity.size
  val maxPop = popularity.max

  printf("\nEl promedio de popularity es: %s\n" +
    "El valor minimo de popularity es: %s\n" +
    "El valor maximo de popularity es %s\n",
    PromPop, minPop, maxPop)

  //revenue
  val revenue = data.flatMap(elem => elem.get("popularity")).map(_.toDouble)
  val minRevenue = revenue.filter(_ > 0).min
  val PromRevenue = revenue.sum / revenue.size
  val maxRevenue = revenue.max

  printf("\nEl promedio de revenue es: %s\n" +
    "El valor minimo de revenue es: %s\n" +
    "El valor maximo de revenue es %s\n",
    PromRevenue, minRevenue, maxRevenue)


  //runtime
  val runtime = data.flatMap(elem => elem.get("runtime")).filter(_.isEmpty != true).map(_.toDouble)
  val minRuntime = runtime.filter(_ > 0).min
  val PromRuntime = runtime.sum.toDouble / runtime.size
  val maxRuntime = runtime.max
  printf("\nEl promedio de runtime es: %s\n" +
    "El valor minimo de runtime es: %s\n" +
    "El valor maximo de runtime es %s\n",
    PromRuntime, minRuntime, maxRuntime)

  //vote_average
  val vote_average = data.flatMap(elem => elem.get("vote_average")).map(_.toDouble)
  val minVa = vote_average.filter(_ > 0).min
  val PromVa = vote_average.sum / vote_average.size
  val maxVa = vote_average.max

  printf("\nEl promedio de vote_average es: %s\n" +
    "El valor minimo de vote_average es: %s\n" +
    "El valor maximo de vote_average es %s\n",
    PromVa, minVa, maxVa)

  //vote_count
  val vote_count = data.flatMap(elem => elem.get("vote_count")).map(_.toInt)
  val minVc = vote_count.filter(_ > 0).min
  val PromVc = vote_count.sum.toDouble / vote_count.size
  val maxVc = vote_count.max

  printf("\nEl promedio de vote_count es: %s\n" +
    "El valor minimo de vote_count es: %s\n" +
    "El valor maximo de vote_count es %s\n",
    PromVc, minVc, maxVc)

}