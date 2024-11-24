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

import org.slf4j.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@ClientEndpoint
public class ClientEcho {
    Path sourcePath = Paths.get("toBeReceived");
    FileOutputStream fos;
    IAcl aclSetter;
    Session session;
    static Logger logger = LoggerFactory.getLogger(ClientEcho.class);

    @OnOpen
    public void onOpen(Session session){
        try {
//            InetAddress me = InetAddress.getLocalHost();
//            String dottedSquad = me.getHostAddress();
//            session.getBasicRemote().sendText(dottedSquad);
            this.session = session;
            fos = new FileOutputStream(sourcePath.toFile());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer message, boolean isLast){
        try {
            byte[] data = new byte[message.remaining()];
//            System.out.println("Received : " + strIdx + " " + message.remaining());
            message.get(data);

            fos.write(data);

            if(isLast){
                logger.info("File received");
                fos.close();
            }
        } catch (Exception e) {
            logger.error("error occured during receiving file");
            e.printStackTrace();
        }
    }

    @OnMessage
    public void handleJSON(String json){
        ObjectMapper mapper = new ObjectMapper();
        try{
            UserFile userFile = mapper.readValue(json, UserFile.class);
            Path targetPath = Paths.get(userFile.getFilePath());
            
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            this.aclSetter = new Acl();
            this.aclSetter.setRwxAcl(targetPath, userFile.getUser());
            logger.info("ACL set");
        } catch(Exception e){
            logger.info("error occured during setting ACL");
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){
        System.out.println("Connection closed");
    }
}
