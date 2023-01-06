import play.api.libs.json._
import com.github.tototoshi.csv._
import java.io.File

object Main extends App {

  val reader = CSVReader.open(new File("movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  val json: JsValue = Json.parse(
    """
   {
      "squadName": "Super hero squad",
      "homeTown": "Metro City",
      "formed": 2016,
      "secretBase": "Super tower",
      "active": true,
      "members": [
        {
          "name": "Molecule Man",
          "age": 29,
          "secretIdentity": "Dan Jukes",
          "powers": [
            "Radiation resistance",
            "Turning tiny",
            "Radiation blast"
          ]
        },
        {
          "name": "Madame Uppercut",
          "age": 39,
          "secretIdentity": "Jane Wilson",
          "powers": [
            "Million tonne punch",
            "Damage resistance",
            "Superhuman reflexes"
          ]
        },
        {
          "name": "Eternal Flame",
          "age": 1000000,
          "secretIdentity": "Unknown",
          "powers": [
            "Immortality",
            "Heat Immunity",
            "Teleportation"
          ]
        }
      ]
    }
       """)

  val name = (json \ "squadName").get
  val hometown = (json \ "homeTown").get
  val formed = (json \ "formed").get
  val secretBase = (json \ "secretBase").get
  val active = (json \ "active").get

  /*
  printf(
    "Nombre del escuadron: %s\n" +
      "Lugar de origen: %s\n" +
      "Fecha de origen: %s\n" +
      "Ubicacion de su base: %s\n" +
      "Activos: %s\n",
    name, hometown, formed, secretBase, active)
 */

  val json_pcomp = data.flatMap(elem => (elem.get("production_companies"))).map(Json.parse)
  val name_pcomp = json_pcomp.flatMap(_ \\ "name").map(_.as[String])
  val companie_ordered = name_pcomp.groupBy(x => x).map(x => (x._1, x._2.length)).toList.sortBy(_._2)
  printf("%s\n\n",companie_ordered)

  val json_pcount = data.flatMap(elem => (elem.get("production_countries"))).map(Json.parse)
  val name_pcount = json_pcount.flatMap(_ \\ "name").map(_.as[String])
  val countrie_ordered = name_pcount.groupBy(x => x).map(x => (x._1, x._2.length)).toList.sortBy(_._2)
  printf("%s\n\n",countrie_ordered)

  val json_slang = data.flatMap(elem => (elem.get("spoken_languages"))).map(Json.parse)
  val name_slang = json_slang.flatMap(_ \\ "name").map(_.as[String])
  val language_ordered = name_slang.groupBy(x => x).map(x => (x._1, x._2.length)).toList.sortBy(_._2)
  printf("%s\n\n",language_ordered)

/*
  val json_crew = data.flatMap(elem => (elem.get("crew"))).map(Json.parse)
  val name_crew = json_crew.flatMap(_ \\ "name").map(_.as[String])
  val gender_crew = json_crew.flatMap(_ \\ "gender").map(_.as[String])
  val department_crew = json_crew.flatMap(_ \\ "department").map(_.as[String])
  val job_crew = json_crew.flatMap(_ \\ "job").map(_.as[String])
*/



}

