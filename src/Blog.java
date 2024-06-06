import com.mongodb.client.AggregateIterable;
import org.bson.Document;

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

        Comment comments = new Comment(connect,indexDocument.getInteger("blogId"), user);
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
                    System.out.println("Do you want to update your title?");
                    choice = myObj.nextLine();
                    if(choice.equals("yes")){
                        System.out.println("Whats the new title?");
                        title = myObj.nextLine();
                    }
                    choice = "";
                    System.out.println("Do you want to update the body?");
                    choice = myObj.nextLine();
                    if(choice.equals("yes")){
                        System.out.println("Whats the new body?");
                        body = myObj.nextLine();
                    }
                    if(!title.isEmpty() && !body.isEmpty()){
                        update= new Document("$set",new Document("title",title).append("body",body));
                    }else if (!title.isEmpty()){
                        update= new Document("$set",new Document("title",title));
                    }else if (!body.isEmpty()){
                        update= new Document("$set",new Document("body",body));
                    }else{
                        System.out.println("Nothing was updated");
                        return;
                    }

                    connect.updateBlog(document,update);
                    System.out.println("Blog successfully updated");
                    myBlogs = connect.getMyBlogs();
                    allBlogs = connect.getBlogs();
                    break;
                }
                currIndex+=1;
            }
        }else{
            System.out.println("Couldnt find the Blog");

        }

    }
}

