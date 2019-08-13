/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.Spring.width;

/**
 *
 * @author will2
 */
public class ClientGUI {
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel contentPanel = new JPanel(new BorderLayout());
    private JFrame frame = new JFrame("Chat System");
    private JTextArea chatContent = new JTextArea();
    private JTextField chatInputField = new JTextField();
    private JButton sendButton = new JButton("Send");
    private JPanel chatPanel = new JPanel();
    private JPanel sidePanel = new JPanel();
    private JList listClientField = new JList();
    private JScrollPane sideScrollPane = new JScrollPane(listClientField);
    private JScrollPane chatScrollPane = new JScrollPane(chatContent);
    
    private String clientUsername;
    private PrintWriter writer;


    public ClientGUI() {
        Frame();
        MainPanel(); //added to frame
        SidePanel(); //added to frame
        ChatPanel(); //added to chatPanel
        
            

        clientUsername = JOptionPane.showInputDialog(null, "Username:");
        Client client = new Client(this);
        client.startClient(clientUsername);
        
        try{
            writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error at PrintWriter in constructor: " + e);
        }
        
        
        frame.setVisible(true);
    }
    
    public void Frame(){
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(1600, 1100);
       
    }
    
    public void MainPanel(){
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setLayout(new BorderLayout());
        //initalPanel.setBorder(BorderFactory.createLineBorder(Color.RED));  
        
        chatContent.setPreferredSize(new Dimension(300,300));
        chatContent.setBackground(Color.GRAY);
        chatContent.setEditable(false);
        mainPanel.add(chatContent, BorderLayout.CENTER); //the chat JTextArea
        
        frame.add(mainPanel);
        
    }
    
    public void ChatPanel(){
        mainPanel.add(chatPanel, BorderLayout.SOUTH);  

        chatInputField.setPreferredSize(new Dimension(600, 100));
        chatPanel.add(chatInputField, BorderLayout.WEST);
        
        sendButton.setPreferredSize(new Dimension(150,100));
        sendButton.setFont(new Font("Arial", Font.PLAIN, 20));
        sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                chatInputField.setBackground(Color.ORANGE);
                
                if (chatInputField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Message is empty");
                    
                } else {
                    //if (chatContent.getText().equals("") || chatContent == null) {
                        String message = chatInputField.getText();
                        
                        chatContent.append("(" + clientUsername + "): " + message);
                        writer.println("(" + clientUsername + "): " + message + "\r\n");
                        chatInputField.setText(null);
                        
                    //}
                }
            }
        });
        
        
        chatPanel.add(sendButton, BorderLayout.CENTER);
                
    }
    
    public void SidePanel(){
        sidePanel.setBackground(Color.BLACK);
        
        sideScrollPane.setPreferredSize(new Dimension(200, 1025));
        sidePanel.add(sideScrollPane);

        frame.add(sidePanel, BorderLayout.EAST);
    }
    
    public void printToChat(String message){
        if (chatContent.getText().equals("") || chatContent == null) {
            chatContent.append("/r/n" + message);
            
        }
    }
    
    
    
    
    public static void main(String[] args) {
       ClientGUI application = new ClientGUI();
        
    }
}
