/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author will2
 */
public class Client {
    public static final String HOST_NAME = "172.28.50.73";
    public static final int HOST_PORT = 5555;
    private Thread clientInputThread;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    
    public Client(ClientGUI gui){
        
    }
    
    public void startClient(String username) {
        try {
            socket = new Socket(HOST_NAME, HOST_PORT);
            System.out.println("You have successfully connected to the server " + socket.getRemoteSocketAddress());
            
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error at the reader/writer streams: " + e);
            }
            
            writer.println(username);
            
            clientInputThread = new Thread(new ReadThread(this, reader));
            clientInputThread.start();
        } catch (IOException e) {
            System.err.println("Error at startClient: " + e.getMessage());
        }
    }
    
    public void closeClient() {
        try{
            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("ClosingClient failed to close: " + e);
        }
        
        System.exit(0);
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public ClientGUI getGui() {
        return gui;
    }
    
    
}
