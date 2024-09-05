package org.example;

import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import java.net.InetAddress;
import java.net.URI;

public class DocClient{
    public static void main(String[] args) throws Exception{
        ClientManager client = ClientManager.createClient();
        client.connectToServer(ClientEcho.class, new URI("ws://localhost:8025/websockets/echo"));
    }
}