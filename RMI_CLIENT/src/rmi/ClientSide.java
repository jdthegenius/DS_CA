package rmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientSide{
    static Scanner scanner=new Scanner(System.in);
    static Socket socket;
    static ArrayList<Email> inbox=new ArrayList<>();
    static ArrayList<Email> spam=new ArrayList<>();
    static User session=null;
    static ObjectOutputStream streamOut;
    static ObjectInputStream streamIn;
    public static void main(String[] args){
        startConnection();
        mainMenu();
    }

    public static void startConnection() {
        socket = null;
        try {
            socket = new Socket("localhost",9147);
            streamOut = new ObjectOutputStream(socket.getOutputStream());
            streamIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void mainMenu(){
        System.out.println("*****************EMAIL APPLICATION**************");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter choice: ");
        String input=scanner.next();
        switch(input){
            case "1":
                register();
                mainMenu();
                break;
            case "2":
                login();
                break;
            default:
                System.out.println("Invalid option");
                mainMenu();
        }
    }
    private static void register(){
        System.out.println("****REGISTRATION*****");
        System.out.print("Enter username: ");
        String username=scanner.next();
        System.out.print("Enter name: ");
        String name=scanner.next();
        System.out.print("Enter email: ");
        String email=scanner.next();
        System.out.print("Enter password: ");
        String password=scanner.next();
        User user=new User(username,name,email,password);
        try {
            ObjectOutputStream streamOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream streamIn = new ObjectInputStream(socket.getInputStream());
            HashMap<String,User> data=new HashMap(); 
            data.put("registration",user);
            streamOut.writeObject(data);
        } catch (IOException e) {
            System.out.println("Connection Failed");
        }
    }
    
    private static void login(){
        System.out.println("****LOGIN*****");
        System.out.print("Enter username: ");
        String username=scanner.next();
        System.out.print("Enter password: ");
        String password=scanner.next();
        User user=new User(username,password);
        try {
            //ObjectOutputStream streamOut = new ObjectOutputStream(socket.getOutputStream());
            //ObjectInputStream streamIn = new ObjectInputStream(socket.getInputStream());
            HashMap<String,User> data=new HashMap(); 
            data.put("login",user);
            streamOut.writeObject(data);
            User results=(User)streamIn.readObject();
            if(results==null){
                System.out.println("Invalid username or password");
                mainMenu();
            }else{
                session=results;
                System.out.println("Login Successful");
                mailMenu();
                
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static boolean verifyNumber(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
            
    }
    public static void mailMenu(){
        System.out.println("******MAIL APPLICATION********");
        System.out.println("1. Send email\n2. View Inbox\n3. View Spam\n4. Exit");
        System.out.print("Enter choice: ");
        String choice=scanner.next();
        switch(choice){
            case "1":
                new SendEmail(streamOut,streamIn).setVisible(true);
                mailMenu();
                break;
            case "2":
                new Inbox(streamOut,streamIn,session).setVisible(true);
                mailMenu();
                break;
            case "3":
                new Spam().setVisible(true);
                mailMenu();
                break;
            case "4":
                System.exit(0);
                break;
        }
    }
}
