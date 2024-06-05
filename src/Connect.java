import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.List;

public class Connect {
    private String url;
    private String database;
    private String userCollection, blogCollection, commentCollection;
    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public User setUser(String username,String password){
        collection = mongoDatabase.getCollection(userCollection);
        AggregateIterable<Document> result = collection.aggregate(List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password)
                ))
        ));
        Document document = result.first();
        if (document!=null){
            System.out.println("Successfully login!");
            return new User(document.getInteger("userID"));
        }
        return null;
    }

    public Connect() {
        this.url = "mongodb://localhost:27017";
        database = "blog";
        userCollection = "user";
        blogCollection = "blogs";
        commentCollection = "comments";
        mongoClient = MongoClients.create(url);
        mongoDatabase = mongoClient.getDatabase(database);

    }
}
