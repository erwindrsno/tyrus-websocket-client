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
//            InetAddress me = InetAddress.getLocalHost();
//            String dottedSquad = me.getHostAddress();
//            session.getBasicRemote().sendText(dottedSquad);
            fos = new FileOutputStream(sourcePath.toFile());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer message, boolean isLast){
        try {
//            int idx = 1;
            //Save the received file to disk
            byte[] data = new byte[message.remaining()];
//            System.out.println("Received : " + strIdx + " " + message.remaining());
            message.get(data);

            fos.write(data);

            if(isLast){
                System.out.println("File received!");
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
        System.out.println("Connection closed");
    }
}
