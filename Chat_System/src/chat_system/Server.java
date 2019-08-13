/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author will2
 */
public class Server {
    private int min;
    private int max;
    private boolean stopRequested = false;
    private Random randomGenerator;
    private static final int PORT = 5555; //hardcoded port number tbc by marker
    public static Vector clientList = new Vector();

    public Server(int min, int max){
        this.min = min;
        this.max = max;
        stopRequested = false;
        randomGenerator = new Random();
                
    }
    
    public void startServer() {
        stopRequested = false; 
        ServerSocket serverSocket = null;
        
        //start server 
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started at " + InetAddress.getLocalHost() + " on port " + PORT);
            
        } catch (IOException e){
            System.err.println("Server cannot listen on port: " + e);
            System.exit(-1);
        }
        
        try {
            while (!stopRequested){
                //waiting until a client connects 
                Socket socket = serverSocket.accept(); 
                //successful connection
                System.out.println("Connection has been made with " + socket.getInetAddress()); 

//                ClientThread newClient = new ClientThread(this, socket);
//                Thread newClientThread = new Thread(newClient);
//                newClientThread.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                
                clientList.add(writer);
                
                while(true) {
                    String data1 = reader.readLine();
                    System.out.println("Received: " + data1);
                    
                    for (int i = 0; clientList.size() < 10; i++) {
                        try {
                            BufferedWriter bw = (BufferedWriter)clientList.get(i);
                            bw.write(data1);
                            bw.write("/r/n");
                            bw.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            serverSocket.close();
                
        } catch(IOException e) {
            System.err.println("Cannot accept client connetion: " + e);
        }
        
        //when cient has requested to stop
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
