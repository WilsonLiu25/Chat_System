/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.io.BufferedReader;
import java.io.IOException;

import java.net.Socket;

/**
 *
 * @author will2
 */
public class ReadThread implements Runnable{
    private Client client;
    private BufferedReader reader;
    
    public ReadThread(Client client, BufferedReader reader) {
        this.client = client;
        this.reader = reader;
    }
    
    @Override
    public void run() {
        String response = "";
        
        while(true) {
            try {
                response = reader.readLine();
                
            } catch (IOException e) {
                System.err.println("Error at run() reciviing message: " + e);
                break;
            }
            
            if (!response.equals("")) {
                System.out.println(response);
                client.getGui().printToChat(response);
                
            }
        }
    }
}
