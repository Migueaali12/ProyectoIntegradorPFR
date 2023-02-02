import com.github.tototoshi.csv._
import java.io.File

object CStrings extends App {

  val reader = CSVReader.open(new File("src/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //genres
  val genres = data.flatMap(x => x.get("genres"))
    .groupBy(genre => genre)
    .map(x => (x._1, x._2.size))

  printf("Los generos son: %s\n" +
    "El genero menos utilizado es: %s con: %s de apariciones\n" +
    "El genero mas utilizado es: %s con: %s de apariciones\n",
    genres.keySet, genres.minBy(_._2)._1, genres.minBy(_._2)._2, genres.maxBy(_._2)._1,
    genres.maxBy(_._2)._2)

  //release_date
  val release_date = data.flatMap(x => x.get("release_date"))
    .groupBy(date => date)
    .map(x => (x._1, x._2.size))

  printf("\nLas fechas de salida son: %s\n" +
    "La fecha menos lanzamientos fue: %s con: %s de apariciones\n" +
    "La fecha con mas lanzamientos fue: %s con: %s de apariciones\n",
    release_date.keySet, release_date.minBy(_._2)._1, release_date.minBy(_._2)._2,
    release_date.maxBy(_._2)._1, release_date.maxBy(_._2)._2)

  //status
  val status = data.flatMap(x => x.get("status"))
    .groupBy(status => status)
    .map(x => (x._1, x._2.size))

  printf("\nLos estados de las películas son: %s\n" +
    "El estado menos utilizado fue: %s con: %s de apariciones\n" +
    "El estado mas utilizado fue: %s con: %s de apariciones\n" ,
    status.keySet, status.minBy(_._2)._1, status.minBy(_._2)._2,
    status.maxBy(_._2)._1, status.maxBy(_._2)._2)

  //title
  val title = data.flatMap(x => x.get("title"))
    .groupBy(title => title)
    .map(x => (x._1, x._2.size))

  printf("\nLos titulos de las películas son: %s\n" +
    "El titulo con menos apariciones fue: %s con: %s de apariciones\n" +
    "El titulo con mas apariciones fue: %s con: %s de apariciones\n",
    title.keySet, title.minBy(_._2)._1, title.minBy(_._2)._2,
    title.maxBy(_._2)._1, title.maxBy(_._2)._2)

  //director
  val director = data.flatMap(x => x.get("director"))
    .groupBy(director => director)
    .map(x => (x._1, x._2.size))

  printf("\nLos directores de las películas son: %s\n" +
    "El director con menos apariciones fue: %s con: %s de apariciones\n" +
    "El director con mas apariciones fue: %s con: %s de apariciones\n",
    director.keySet, director.minBy(_._2)._1, director.minBy(_._2)._2,
    director.maxBy(_._2)._1, director.maxBy(_._2)._2)

}
