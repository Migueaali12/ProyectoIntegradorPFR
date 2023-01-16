import com.github.tototoshi.csv._
import java.io.File

object ACtexto extends App {

  val reader = CSVReader.open(new File("movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //genres
  val genres = data.flatMap(x => x.get("genres"))
    .groupBy {
      case genre => genre
    }.map {
    case genre => (genre._1, genre._2.size)
  }

  printf("Los generos son: %s\n" +
    "El genero menos utilizado es: %s con: %s de apariciones\n" +
    "El genero mas utilizado es: %s con: %s de apariciones\n" +
    "%s\n",
    genres.map(_._1).toSet, genres.minBy(_._2)._1, genres.minBy(_._2)._2, genres.maxBy(_._2)._1, genres.maxBy(_._2)._2
    ,genres.toList.sortBy(_._2))

  //release_date
  val release_date = data.flatMap(x => x.get("release_date"))
    .groupBy {
      case date => date
    }.map {
    case date => (date._1, date._2.size)
  }

  printf("\nLas fechas de salida son: %s\n" +
    "La fecha menos lanzamientos fue: %s con: %s de apariciones\n" +
    "La fecha con mas lanzamientos fue: %s con: %s de apariciones\n",
    release_date.map(_._1).toSet, release_date.minBy(_._2)._1, release_date.minBy(_._2)._2,
    release_date.maxBy(_._2)._1, release_date.maxBy(_._2)._2)

  //status
  val status = data.flatMap(x => x.get("status"))
    .groupBy {
      case status => status
    }.map {
    case status => (status._1, status._2.size)
  }

  printf("\nLos estados de las películas son: %s\n" +
    "El estado menos utilizado fue: %s con: %s de apariciones\n" +
    "El estado mas utilizado fue: %s con: %s de apariciones\n" +
    "%s\n",
    status.map(_._1).toSet, status.minBy(_._2)._1, status.minBy(_._2)._2,
    status.maxBy(_._2)._1, status.maxBy(_._2)._2, status.toList.sortBy(_._2))

  //title
  val title = data.flatMap(x => x.get("title"))
    .groupBy {
      case title => title
    }.map {
    case title => (title._1, title._2.size)
  }

  printf("\nLos titulos de las películas son: %s\n" +
    "El titulo con menos apariciones fue: %s con: %s de apariciones\n" +
    "El titulo con mas apariciones fue: %s con: %s de apariciones\n" ,

    title.map(_._1).toSet, title.minBy(_._2)._1, title.minBy(_._2)._2,
    title.maxBy(_._2)._1, title.maxBy(_._2)._2)

  //director
  val director = data.flatMap(x => x.get("director"))
    .groupBy {
      case director => director
    }.map {
    case director => (director._1, director._2.size)
  }

  printf("\nLos directores de las películas son: %s\n" +
    "El director con menos apariciones fue: %s con: %s de apariciones\n" +
    "El director con mas apariciones fue: %s con: %s de apariciones\n" ,
    director.map(_._1).toSet, director.minBy(_._2)._1, director.minBy(_._2)._2,
    director.maxBy(_._2)._1, director.maxBy(_._2)._2)



}