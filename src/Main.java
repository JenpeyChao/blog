import java.util.Scanner;

public class Main {
    private static Connect connect;
    static Scanner myObj = new Scanner(System.in);
    static User user;
    static Blog blog;

    public static void getAccess(){
        do {
            String username, password;
            System.out.print("Username: ");
            username = myObj.nextLine();
            System.out.print("Password: ");
            password = myObj.nextLine();
            user = connect.setUser(username, password);
            blog = new Blog(connect,user);
           if(user== null){
                System.out.println("Please Try again");
           }
        }while(user == null);

        menu();
    }
    public static void menu(){
        int choice;

        blog.showBlogs();
        do {
            System.out.println("Menu:");
            System.out.println("1) Pick a blog");
            System.out.println("2) Update my blog");
            System.out.println("3) Delete my blog");
            System.out.println("4) Create a new blog");
            System.out.println("5) Exit");
            choice = myObj.nextInt();
            myObj.nextLine();
            switch(choice){
                case 1 -> {
                    System.out.print("Which blog do you want to look at? ");
                    int index = myObj.nextInt();
                    myObj.nextLine();
                    blog.pickBlog(index);
                }

                case 2 ->blog.updateBlog();
            }
            blog.showBlogs();
        }while(choice != 5);

    }


    public static void main(String[] args) {
        connect = new Connect();
        getAccess();
    }
}