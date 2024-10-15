package org.example;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.OnClose;

import java.io.*;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@ClientEndpoint
public class ClientEcho {
    Path sourcePath = Paths.get("toBeReceived");
    FileOutputStream fos;
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
            fos = new FileOutputStream(sourcePath.toFile());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer message, boolean isLast){
        try {
            int idx = 1;
            String strIdx = idx + "";
            //Save the received file to disk
            byte[] data = new byte[message.remaining()];
            System.out.println("Remaining : " + strIdx + " " + message.remaining());
            message.get(data);
            //Save to a file (e.g., received_file.pdf)

            fos.write(data);

            if(isLast){
                System.out.println("File transferred!");
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void renameFile(String fileName){
        Path targetPath = Paths.get(fileName);
        try{
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){
        System.out.println("Connection closed by client");
    }
}
