import org.mongodb.scala._
import org.mongodb.scala.result.{DeleteResult, InsertManyResult, InsertOneResult, UpdateResult}
import org.mongodb.scala.Observable
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object MongoExample extends App {

  //Conexión
  val uri: String = "mongodb+srv://MondongoDB:maikibot12@cluster0.robkoss.mongodb.net/test"
  val client: MongoClient = MongoClient(uri)

  val db: MongoDatabase = client.getDatabase("Utpl")
  val collection: MongoCollection[Document] = db.getCollection("Alumnos")

//Select
  def select(collection: MongoCollection[Document]) = {
    val observable = collection.find()
    observable.subscribe(
      (doc: Document) => println("Seleccion realizada con exito\n" + doc.toJson()),
      (e: Throwable) => println("Error en la selección " + e)
    )
  }
  //select(collection)

  //Insert
  def insert(collection: MongoCollection[Document], docs: List[Document]) = {
    val insertObservable = collection.insertMany(docs)
    insertObservable.subscribe(
      (result: InsertManyResult) => println("Insertado con éxito!\n" + docs.map(_.toJson())),
      (e: Throwable) => println("Error al insertar: " + e)
    )
  }
  val doc1 = Document("Nombre" -> "Pedro Aguilar", "Edad" -> 20, "Calificación" -> 9.5)
  val doc2 = Document("Nombre" -> "José Eguiguren", "Edad" -> 25, "Calificación" -> 7.5)
  val doc3 = Document("Nombre" -> "Fernanda Gomez", "Edad" -> 19, "Calificación" -> 9.0)
  val documents = List(doc1, doc2, doc3)
  //insert(collection, documents)

  //Delete
   def delete(collection: MongoCollection[Document], filter: Bson) = {
    val deleteObservable = collection.deleteMany(filter)
    deleteObservable.subscribe(
      (result: DeleteResult) => println("Eliminado con éxito!\n" + filter.toString),
      (e: Throwable) => println("Error al eliminar: " + e)
    )
  }
  //delete(collection, equal("Calificación", 9.5))

  //Update
  def update(collection: MongoCollection[Document], filter: Bson, update: Bson) = {
    val updateObservable = collection.updateOne(filter, update)
    updateObservable.subscribe(
      (result: UpdateResult) => printf("Actualizado con éxito!\n" +
        "Patrón de Filtrado: %s\n" +
        "Actualización: %s\n"
        , filter.toString, update.toString),
      (e: Throwable) => println("Error al actualizar: " + e)
    )
  }
  //update(collection, equal("Nombre", "Juan Perez"), set("Calificación", 10))

  //Crear una coleccíon nueva
  def createCollection(db: MongoDatabase, collectionName: String) = {
    db.createCollection(collectionName).toFuture().onComplete {
      case Success(_) => printf("Colección creada con éxito!\n" +
        "Base de datos: %s\n" +
        "Nombre de la colección nueva: %s\n",
        db.name, collectionName)
      case Failure(e) => println("Error al crear la colección: " + e)
    }
  }
  createCollection(db, "Alumnos (Distancia)")

}
