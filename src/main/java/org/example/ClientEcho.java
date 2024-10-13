package org.example;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.OnClose;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.ByteBuffer;

@ClientEndpoint
public class ClientEcho {
    @OnOpen
    public void onOpen(Session session){
        try {
            InetAddress me = InetAddress.getLocalHost();
            String dottedSquad = me.getHostAddress();
            session.getBasicRemote().sendText(dottedSquad);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            //System.out.println("Type yes to receive file from server");
            //String command = reader.readLine();
            //session.getBasicRemote().sendText(command);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer message){
        try {
            //Save the received file to disk
            byte[] data = new byte[message.remaining()];
            message.get(data);
            //Save to a file (e.g., received_file.pdf)
            try (FileOutputStream fos = new FileOutputStream("received_file.txt")) {
                fos.write(data);
                System.out.println("File saved as received_file.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){
        System.out.println("Connection closedeafvesdbhrewhgnvijinulh");
    }
}
