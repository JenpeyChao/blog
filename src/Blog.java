import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.Scanner;

public class Blog {
    //udpate blogs
    //show all/my blogs
    private final Connect connect;
    private AggregateIterable<Document> myBlogs;
    private AggregateIterable<Document> allBlogs;
    private User user;
    private Scanner myObj = new Scanner(System.in);

    public Blog(Connect connect,User user) {
        this.connect = connect;
        myBlogs = connect.getMyBlogs();
        allBlogs = connect.getBlogs();
        this.user = user;
    }
    public void showBlogs(){
        AggregateIterable<Document> result = allBlogs;
        if(result.first() !=null){
            int index = 0;
            for(Document document: result){

                System.out.print((index+1) +") ");
                System.out.println(document.getString("title"));
                index+=1;
            }

        }else{
            System.out.println("Theres no Blogs to show");
        }

    }

    public void showMyBlogs(){
        System.out.println("----My Blogs----");
        AggregateIterable<Document> result = myBlogs;
        if(result.first() !=null){
            int index = 0;
            for(Document document: result){
                System.out.print((index+1) +") ");
                System.out.println(document.getString("title"));
                index+=1;
            }
        }else{
            System.out.println("Theres no Blogs to show");
        }

    }

    public void pickBlog(int index) {
        AggregateIterable<Document> result = allBlogs;
        int currIndex = 1;
        Document indexDocument = null;
        if(result.first() !=null){

            for(Document document: result){
                if(currIndex == index){
                    indexDocument = document;
                    break;
                }
                currIndex+=1;
            }
        }else{
            System.out.println("Theres no blogs to pick from");
            return;
        }

        if(currIndex < index || indexDocument == null){
            System.out.println("Couldn't find that blog");
            return;
        }

        Comment comments = new Comment(connect, indexDocument.getObjectId("_id"), user);
        System.out.println("-----BLOG-----");
        System.out.println("Title: "+ indexDocument.getString("title"));
        System.out.println("Body: "+ indexDocument.getString("body"));
        comments.getComments();

    }

    public void updateBlog() {
        showMyBlogs();
        System.out.println("Which blog do you want to update?");
        int index = myObj.nextInt();
        myObj.nextLine();
        AggregateIterable<Document> result = myBlogs;
        int currIndex = 1;

        if(result.first() !=null){

            for(Document document: result){
                if(currIndex == index){
                    String body="",title = "";
                    String choice;
                    Document update = null;

                    System.out.println("Do you want to update your title or the body?");
                    choice = myObj.nextLine();

                    if(choice.equals("title")){
                        System.out.println("Whats the new title?");
                        title = myObj.nextLine();
                        update= new Document("$set",new Document("title",title));

                    }else if(choice.equals("body")){
                        System.out.println("Whats the new body?");
                        body = myObj.nextLine();
                        update = new Document("$set",new Document("body",body));

                        System.out.println("Body successfully updated");
                    }
                    if(choice.equals("title") || choice.equals("body")){
                        connect.updateBlog(document,update);
                        myBlogs = connect.getMyBlogs();
                        allBlogs = connect.getBlogs();
                    }

                    break;
                }
                currIndex+=1;
            }
        }else{
            System.out.println("Couldnt find the Blog");

        }

    }

    public void deleteBlog() {
        showMyBlogs();
        System.out.println("Which blog do you want to delete?");
        int index = myObj.nextInt();
        myObj.nextLine();
        AggregateIterable<Document> result = myBlogs;
        int currIndex = 1;
        if(result == null){
            System.out.println("Theres nothing to delete");
            return;
        }
        if(result.first() !=null){

            for(Document document: result) {
                if (currIndex == index) {
                    connect.deleteBlog(new Document("blogId",document.get("blogId")));
                    myBlogs = connect.getMyBlogs();
                    allBlogs = connect.getBlogs();
                }
            }
        }

    }

    public void addBlog(){
        String title,body;
        System.out.println("whats they title?");
        title = myObj.nextLine();
        System.out.println("Whats the body?");
        body= myObj.nextLine();
        Document document = new Document()
                .append("blogId", 1)
                .append("userId",user.getId())
                .append("title",title)
                .append("body",body);
        connect.addBlog(document);
        myBlogs = connect.getMyBlogs();
        allBlogs = connect.getBlogs();
    }
}

