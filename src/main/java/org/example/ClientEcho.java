package org.example;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;

import java.io.IOException;
import java.net.InetAddress;

@ClientEndpoint
public class ClientEcho {
    @OnOpen
    public void onOpen(Session session){
        try {
            InetAddress me = InetAddress.getLocalHost();
            String dottedSquad = me.getHostAddress();
            session.getBasicRemote().sendText(dottedSquad);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
