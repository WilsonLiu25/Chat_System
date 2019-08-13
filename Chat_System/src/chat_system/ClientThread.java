/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author will2
 */
public class ClientThread implements Runnable{
    private Server server;
    private Socket socket;
    
    private PrintWriter writer;
    private BufferedReader reader;
    private String userName;
    
    public ClientThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            InputStream userInput = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(userInput));
        
            OutputStream userOutput = socket.getOutputStream();
            writer = new PrintWriter(userOutput, true);
        
            userName = reader.readLine();
            System.out.println("A new user has joined the sever, Welcome, " + userName + " !");
        
            server.addClient(this);
        
            String clientMessage = "";
            do {
                clientMessage = reader.readLine();
                if (clientMessage != null) {
                    server.broadcast(clientMessage + "/r/n", this); 
                }
            } while (true);
            
        } catch(IOException e) {
            System.out.println("Error in run(): " + e);
        }
    }
    
    public String getUsername(){
        return userName;
    }
    
    public void setUsername(String name){
        userName = name;
    }
    
    public void sendMessage(String message) {
        writer.println(message);
    }
}
