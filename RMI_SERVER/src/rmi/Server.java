package rmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final int PORT = 9147; //port
    static ArrayList<Email> emails=new ArrayList<>();
    public static void main(String[] args) throws Exception {
        System.out.println("Server started and running.....");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }

    }
    /**
     handler class to deal with objects from the serverCoordinator
     */
    private static class Handler extends Thread {
        private final Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        Loader loader=new Loader();
        
  
        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                //object streams
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                HashMap<String,Object> received=(HashMap)in.readObject();
                System.out.println(received.keySet().toString());
                if(received.keySet().toString().equals("[registration]")){
                    User user= (User) received.get("registration");
                    loader.appendData("users.txt", user.toString());
                    out.writeObject(user);
                }else if(received.keySet().toString().equals("[login]")){
                    User user= (User) received.get("login");
                    String[] existing=loader.readFile("users.txt");
                    User status=null;
                    for(int i=0;i<existing.length;i++){
                        String[] line=existing[i].split("~");
                        User temp=new User(line[0],line[1],line[2],line[3]);
                        if(temp.getUsername().equals(user.getUsername()) && temp.getPassword().equals(user.getPassword())){
                            status=temp;
                        }
                    }
                    out.writeObject(status);
                }else if(received.keySet().toString().equals("[send]")){
                    Email email=(Email)received.get("send");
                    emails.add(email);
                    //loader.SaveEmails(emails);
                    System.out.println("THe server has stored an email and the current size is "+ emails.size());
                }else if(received.keySet().toString().equals("[fetch]")){
                    
                    System.out.println("The server has received a fetch request and the current size of the inbox is "+emails.size());
                    User session=(User)received.get("fetch");
                    ArrayList<Email> inbox=new ArrayList<>();
                    for(Email email:emails){
                        if(email.getRecipient().equals(session.getEmail())){
                            inbox.add(email);
                            System.out.println(email.getMail()+" ---- Added");
                        }else{
                            System.out.println(email.getRecipient()+"====="+session.getEmail());
                        }
                    }
                    System.out.println("The size to be sent back is "+inbox.size());
                    out.writeObject(inbox);
                }
            } catch (SocketException | NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(HashMap.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }

        }

    }
}


