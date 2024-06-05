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
//            if(user.getRole().isEmpty()){
//                System.out.println("Please Try again");
//            }
        }while(user.getId() == -1);
//        menu(user.getRole());
    }
    public static void main(String[] args) {
        connect = new Connect();
        getAccess();
    }
}