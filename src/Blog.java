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
    public boolean showBlogs(){
        //prints all the blogs
        System.out.println("----Blogs----");
        AggregateIterable<Document> result = allBlogs;
        if(result !=null){
            int index = 0;
            for(Document document: result){

                System.out.print((index+1) +") ");
                System.out.println(document.getString("title"));
                index+=1;
            }
            return true;

        }else{
            System.out.println("Theres no Blogs to show");
        }
        return false;
    }

    public boolean showMyBlogs(){
        //pints all the blogs the user has
        System.out.println("----My Blogs----");
        AggregateIterable<Document> result = myBlogs;
        if(result !=null){
            int index = 0;
            for(Document document: result){
                System.out.print((index+1) +") ");
                System.out.println(document.getString("title"));
                index+=1;
            }
            return true;
        }else{
            System.out.println("Theres no Blogs to show");
        }
        return false;
    }

    public void pickBlog(int index) {
        //get the blog the user picked
        AggregateIterable<Document> result = allBlogs;
        int currIndex = 1;
        Document indexDocument = null;
        if(result!=null){

            for(Document document: result){
                if(currIndex == index){
                    indexDocument = document;
                    break;
                }
                currIndex+=1;
            }
        }else{
            //returns if theres no blogs
            System.out.println("Theres no blogs to pick from");
            return;
        }
        //returns if the blog isnt foudn
        if(currIndex < index || indexDocument == null){
            System.out.println("Couldn't find that blog");
            return;
        }
        //shows the blog then gets the comments from the blog
        Comment comments = new Comment(connect, indexDocument.getObjectId("_id"), user);
        System.out.println("-----BLOG-----");
        System.out.println("Title: "+ indexDocument.getString("title"));
        System.out.println("Body: "+ indexDocument.getString("body"));
        comments.getComments();

    }

    public void updateBlog() {
        // if theres blogs you can update
        if(showMyBlogs()) {
            //asks which blog you want to update
            System.out.println("Which blog do you want to update?");
            int index = myObj.nextInt();
            myObj.nextLine();
            AggregateIterable<Document> result = myBlogs;
            int currIndex = 1;

            if (result.first() != null) {

                for (Document document : result) {
                    if (currIndex == index) {
                        //find the blog
                        String body = "", title = "";
                        String choice;
                        Document update = null;
                        //asks which part of the blog you want to update, ther title or body
                        System.out.println("Do you want to update your title or the body?");
                        choice = myObj.nextLine();
                        //if they choose the title then we get what they want to change it to
                        if (choice.equals("title")) {
                            System.out.println("Whats the new title?");
                            title = myObj.nextLine();
                            update = new Document("$set", new Document("title", title));

                        //if they choose the body then we get what they want to change it to
                        } else if (choice.equals("body")) {
                            System.out.println("Whats the new body?");
                            body = myObj.nextLine();
                            update = new Document("$set", new Document("body", body));


                        }
                        //then changes the part they chose
                        if (choice.equals("title") || choice.equals("body")) {
                            connect.updateBlog(document, update);
                            myBlogs = connect.getMyBlogs();
                            allBlogs = connect.getBlogs();
                        }

                        break;
                    }
                    currIndex += 1;
                }
            } else {
                System.out.println("Couldnt find the Blog");

            }
        }

    }

    public void deleteBlog() {
        //if theres blogs to print out
        if (showMyBlogs()) {
            //then user picks which one they want to delete
            System.out.println("Which blog do you want to delete?");
            int index = myObj.nextInt();
            myObj.nextLine();
            AggregateIterable<Document> result = myBlogs;
            int currIndex = 1;

            if (result.first() != null) {
                //find the blog you want to delete
                for (Document document : result) {
                    if (currIndex == index) {
                        //deletes the blog and returns
                        connect.deleteBlog(new Document("blogId", document.get("blogId")));
                        myBlogs = connect.getMyBlogs();
                        allBlogs = connect.getBlogs();
                        return;
                    }
                }
            }
        }else {
            System.out.println("Theres no Blogs to delete");
        }

    }

    public void addBlog(){
        //asks the title and body of the blog they want to add
        String title,body;
        System.out.println("whats they title?");
        title = myObj.nextLine();
        System.out.println("Whats the body?");
        body= myObj.nextLine();
        Document document = new Document()
                .append("userId",user.getId())
                .append("title",title)
                .append("body",body);
        connect.addBlog(document);
        myBlogs = connect.getMyBlogs();
        allBlogs = connect.getBlogs();
    }
}

