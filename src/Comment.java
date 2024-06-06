import com.mongodb.client.AggregateIterable;
import org.bson.Document;

import java.util.Scanner;

public class Comment {
    private Connect connect;
    private Integer blogId;
    private AggregateIterable<Document> comments;
    private User user;
    private Scanner myObj = new Scanner(System.in);

    public Comment(Connect connect, Integer blogId, User user) {
        this.connect = connect;
        this.blogId = blogId;
        this.user = user;
    }

    public void getComments() {
        comments = connect.getComments(blogId);
        System.out.println("-----COMMENTS-----");
        if(comments == null){
            System.out.println("Theres no comments");
        }else{
            int index = 0;
            for(Document document: comments){
                System.out.print("From: "+document.getInteger("userId"));
                System.out.println(" Says:"+document.getString("body"));
            }

        }
        String choice;
        System.out.print("Would you like to add a comment to this blog?(yes/no) ");
        choice = myObj.nextLine();
        while(choice.equals("yes")) {
            System.out.println("What would you like to say?");
            String body = myObj.nextLine();
            Document document = new Document().append("userId",user.getId())
                    .append("blogId",blogId)
                    .append("body",body);
            connect.addComment(document);
            System.out.print("Would you like to add another comment?(yes/no) ");
            choice = myObj.nextLine();
        }
    }
}
