import com.mongodb.client.AggregateIterable;
import org.bson.Document;

public class Blog {
    //udpate blogs
    //show all/my blogs
    private final Connect connect;
    private AggregateIterable<Document> myBlogs;
    private AggregateIterable<Document> allBlogs;
    private User user;

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
        AggregateIterable<Document> result = myBlogs;
        if(result.first() !=null){
            int index = 0;
            for(Document document: result){
                System.out.print((index+1) +") ");
                System.out.println(document.getString("title"));
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
                System.out.print(currIndex +") ");
                System.out.println(document.getString("title"));
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

    }
}

