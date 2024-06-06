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
    private int userID;

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
            System.out.println(username+ " Successfully login!");
            userID = document.getInteger("userID");
            return new User(userID);
        }
        return null;
    }
    public AggregateIterable<Document> getBlogs(){
        collection = mongoDatabase.getCollection(blogCollection);
        AggregateIterable<Document> result = collection.aggregate(List.of(
                Aggregates.match(new Document())
        ));
        if(result.first()!= null){
            return result;
        }
        return null;
    }
    public AggregateIterable<Document> getMyBlogs(){
        collection = mongoDatabase.getCollection(blogCollection);
        AggregateIterable<Document> result = collection.aggregate(List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("userId", userID)
                ))

        ));
        if(result.first()!= null){
            return result;
        }
        return null;
    }

    public AggregateIterable<Document> getComments(Integer blogId) {
        collection = mongoDatabase.getCollection(commentCollection);
        AggregateIterable<Document> result = collection.aggregate(List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("blogId", blogId)
                ))

        ));
        if(result.first()!= null){
            return result;
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


    public void addComment(Document document) {
        collection = mongoDatabase.getCollection(commentCollection);
        collection.insertOne(document);
        System.out.println("Sucessfully Commented!");
    }
}
