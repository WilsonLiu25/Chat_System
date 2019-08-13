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
import java.util.LinkedList;
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
    //public static Vector clientList = new Vector();
    
    //storing users
    private LinkedList<ClientThread> clientThreadsList = new LinkedList<>();

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
                
                //Creating threads for the new clients
                ClientThread newClient = new ClientThread(this, socket);
                Thread newClientThread = new Thread(newClient);
                newClientThread.start();
                
//                while(true) {
//                    String data1 = reader.readLine();
//                    System.out.println("Received: " + data1);
//                    
//                    for (int i = 0; clientList.size() < 10; i++) {
//                        try {
//                            BufferedWriter bw = (BufferedWriter)clientList.get(i);
//                            bw.write(data1);
//                            bw.write("/r/n");
//                            bw.flush();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
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
    
    public String[] clientUsernames(){
        //storing client user names into a string array
        String[] userNames = new String[clientThreadsList.size()];
        
        for (int i = 0; i < (clientThreadsList.size()); i++) {
            userNames[i] = clientThreadsList.get(i).getUsername();
        }
    }
    
    public void addClient(ClientThread user) {
        clientThreadsList.add(user);
    }
    
    public void broadcast(String theMessage, ClientThread exception){
        for (ClientThread client : clientThreadsList) {
            client.sendMessage(theMessage);
        }
    }
    
    public static void main(String[] args) {
        Server server = new Server(1,100);
        server.startServer();
    }
    
}
