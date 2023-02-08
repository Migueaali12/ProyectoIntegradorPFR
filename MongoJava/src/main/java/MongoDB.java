import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

public class MongoDB {
    public static void main(String[] args) {

        //Conexion
        ConnectionString connectionString = new ConnectionString("mongodb+srv://MondongoDB:maikibot12@cluster0" +
                ".robkoss.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        //Obtener base de datos
        MongoDatabase database = mongoClient.getDatabase("Utpl");

        //Obtener Colección
        MongoCollection<Document> collection = database.getCollection("Alumnos");

        //Select
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            System.out.println(doc.toJson());
        }

        //Insert
        Document newDoc = new Document("Nombre", "John Smith")
                .append("Edad", 30)
                .append("Calificación", 7.5);

        collection.insertOne(newDoc);

        //Delete
        Document criterioBus1 = new Document("Nombre", "John Smith");
        collection.deleteOne(criterioBus1);

        //Find
        Document criterioBus2 = new Document("Nombre", "Juan Perez");

        MongoCursor<Document> cursor1 = collection.find(criterioBus2).iterator();

        if (cursor1.hasNext()) {
            Document found = cursor1.next();
            System.out.println(found.toJson());
        } else {
            System.out.println("Elemento no encontrado");
        }

        //Update
        Document criterioBus3 = new Document("Nombre", "Juan Perez");

        Document update = new Document("$set", new Document("Calificación", 8.5));

        collection.updateOne(criterioBus3, update);

    }
}
