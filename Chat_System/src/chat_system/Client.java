/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author will2
 */
public class ClientThread {
    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 7777;
    public String userName;
    
    public ClientThread(){
        
    }
    
    public void startClient() {
        Socket socket = null;
        
        Scanner userInput = new Scanner(System.in);
        
        try {
            socket = new Socket(HOST_NAME, HOST_PORT);
            
            Thread readThread = new Thread(new ReadThread(this, socket));
            readThread.start();
            
            Thread writeThread = new Thread(new WriteThread(this, socket));
            writeThread.start();
  
                    
        } catch (IOException e) {
            System.err.println("Client could not successfully make a connection: " + e);
            System.exit(-1);
        }
        
//        PrintWriter printer; //output stream
//        BufferedReader reader; //input stream
//        
//        try {
//            //create a output stream for the socket
//            printer = new PrintWriter(socket.getOutputStream(), true);
//            
//            //create a bufferred input stream for this socket
//            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//           
//            
//        } catch (IOException e){
//            System.out.println("Client error with games: " + e);
//        }
            
        
    }
    
    public static void main(String[] args) {
        ClientThread clientThread = new ClientThread();
        
    }
    
}
