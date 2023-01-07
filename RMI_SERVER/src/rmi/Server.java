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
        ArrayList<Email> emails=new ArrayList<>();
  
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
                    out.writeUTF("success");
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
                    System.out.println("THe server has stored an email");
                }else if(received.keySet().toString().equals("[send]")){
                    User user=(User)received.get("send");
                    ArrayList<Email> temp=new ArrayList<>();
                    for(int i=0;i<emails.size();i++){
                        if(emails.get(i).getRecipient().equalsIgnoreCase(user.getEmail())){
                            temp.add(emails.get(i));
                        }
                    }
                    out.writeObject(temp);
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


