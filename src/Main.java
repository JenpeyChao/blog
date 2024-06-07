import java.util.Scanner;

public class Main {
    private static Connect connect;
    static Scanner myObj = new Scanner(System.in);
    static User user;
    static Blog blog;

    public static void getAccess(){
        //checks if the user is in the data
        do {
            String username, password;
            System.out.print("Username: ");
            username = myObj.nextLine();
            System.out.print("Password: ");
            password = myObj.nextLine();
            user = connect.setUser(username, password);
            blog = new Blog(connect,user);
           if(user== null){
               //if not then they can signup or just try again
                System.out.println("Theres no user with that credentials");
                System.out.println("Want to sign up?");
                String choice = myObj.nextLine();
                if (choice.equals("yes")){
                    System.out.print("Whats the username? ");
                    username = myObj.nextLine();
                    System.out.print("Whats the password? ");
                    password = myObj.nextLine();
                    connect.newUser(username,password);
                }
           }
        }while(user == null);
        //prints the menu
        menu();
    }
    public static void menu(){
        int choice;
        //prints the menu
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
                case 3-> blog.deleteBlog();
                case 4-> blog.addBlog();
            }
            if(choice != 5){
                blog.showBlogs();
            }

        }while(choice != 5);

    }


    public static void main(String[] args) {
        connect = new Connect();
        getAccess();
    }
}