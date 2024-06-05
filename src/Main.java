import java.util.Scanner;

public class Main {
    private static Connect connect;
    static Scanner myObj = new Scanner(System.in);
    static User user;

    public static void getAccess(){
        do {
            String username, password;
            System.out.print("Username: ");
            username = myObj.nextLine();
            System.out.print("Password: ");
            password = myObj.nextLine();
            user = connect.setUser(username, password);
           if(user.getId() == -1){
                System.out.println("Please Try again");
           }
        }while(user.getId() == -1);
        menu();
    }
    public static void menu(){
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1) Show blogs");
            System.out.println("2) Update my blog");
            System.out.println("3) Delete my blog");
            System.out.println("4) Create a new blog");
            choice = myObj.nextInt();
            myObj.nextLine();
        }while(choice != 5);

    }
    public static void main(String[] args) {
        connect = new Connect();
        getAccess();
    }
}