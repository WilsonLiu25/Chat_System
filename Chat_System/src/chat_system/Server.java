/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author will2
 */
public class Server {
    private int min;
    private int max;
    private boolean stopRequested = false;
    private Random randomGenerator;
    private static final int PORT = 7777; //hardcoded port number tbc by marker

    public Server(int min, int max){
        this.min = min;
        this.max = max;
        stopRequested = false;
        randomGenerator = new Random();
                
    }
    
    public void startServer() {
        stopRequested = false; 
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started at " + InetAddress.getLocalHost() + " on port " + PORT);
            
        } catch (IOException e){
            System.err.println("Server cannot listen on port: " + e);
            System.exit(-1);
        }
        
        try {
            while (!stopRequested){
                System.out.println("test1");
                Socket socket = serverSocket.accept();
                System.out.println("Connection has been made with " + socket.getInetAddress());
                System.out.println("test");
//                ClientThread newClient = new ClientThread(this, socket);
//                Thread newClientThread = new Thread(newClient);
//                newClientThread.start();
            }
            serverSocket.close();
                
        } catch(IOException e) {
            System.err.println("Cannot accept client connetion: " + e);
        }
        
        System.out.println("Server finished");
    }
    
    public void requestStop() {
        stopRequested = true;
    }
    
    public static void main(String[] args) {
        Server server = new Server(1,100);
        server.startServer();
    }
    
}
