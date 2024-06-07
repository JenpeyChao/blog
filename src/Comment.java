import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class Comment {
    private Connect connect;
    private ObjectId blogId;
    private AggregateIterable<Document> comments;
    private User user;
    private Scanner myObj = new Scanner(System.in);

    public Comment(Connect connect, ObjectId blogId, User user) {
        this.connect = connect;
        this.blogId = blogId;
        this.user = user;
    }
    public void printComments(){
        //gets the comments
        comments = connect.getComments(blogId);
        System.out.println("-----COMMENTS-----");
        if(comments == null){
            System.out.println("Theres no comments");
        }else{
            int index = 0;
            for(Document document: comments){
                System.out.print("From: "+document.getString("name"));
                System.out.println(" Says:"+document.getString("body"));
            }

        }
    }
    public void getComments() {
        //gets the
        printComments();
        String choice;
        System.out.print("Would you like to add a comment to this blog?(yes/no) ");
        choice = myObj.nextLine();
        while(choice.equals("yes")) {
            System.out.println("What would you like to say?");
            String body = myObj.nextLine();
            Document document = new Document().append("userId",user.getId())
                    .append("name", user.getName())
                    .append("blogId",blogId)
                    .append("body",body);
            connect.addComment(document);
            printComments();
            System.out.print("Would you like to add another comment?(yes/no) ");
            choice = myObj.nextLine();

        }
    }
}
