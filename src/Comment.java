import com.mongodb.client.AggregateIterable;
import org.bson.Document;

public class Comment {
    private Connect connect;
    private Integer blogId;
    private AggregateIterable<Document> comments;

    public Comment(Connect connect, Integer blogId) {
        this.connect = connect;
        this.blogId = blogId;
    }

    public void getComments() {
        comments = connect.getComments(blogId);
    }
}
