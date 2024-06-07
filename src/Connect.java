import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class Connect {
    private String url;
    private String database;
    private String userCollection, blogCollection, commentCollection;
    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private ObjectId userID;

    public User setUser(String username,String password){
        //connect to the database and returns the user id and name
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
            userID = document.getObjectId("_id");
            return new User(userID, document.getString("username"));
        }
        return null;
    }
    public AggregateIterable<Document> getBlogs(){
        //returns the collection of all the blogs
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
        //returns the collection of the user blogs
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

    public AggregateIterable<Document> getComments(ObjectId blogId) {
        //returns the collection of commetns
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
        //adds the comment to the collection
        collection = mongoDatabase.getCollection(commentCollection);
        collection.insertOne(document);
        System.out.println("Sucessfully Commented!");
    }
    public void updateBlog(Document filter,Document update) {
        //updates the blog
        collection = mongoDatabase.getCollection(blogCollection);
        collection.updateOne(filter, update);
        System.out.println("Blog successfully updated");
    }


    public void deleteBlog(Document document) {
        //deletes the blog
        collection = mongoDatabase.getCollection(blogCollection);
        collection.deleteOne(document);
    }

    public void addBlog(Document document) {
        //adds the blog
        collection = mongoDatabase.getCollection(blogCollection);
        collection.insertOne(document);
        System.out.println("Sucessfully added a new blog!");
    }

    public void newUser(String username, String password) {
        //creates the a new user
        Document document = new Document().append("username",username)
                .append("password", password);
        collection = mongoDatabase.getCollection(userCollection);
        collection.insertOne(document);
        System.out.println("Successfully created an account!");
    }
}
