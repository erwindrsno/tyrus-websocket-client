package org.example;

import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

public class DocClient{
    public static void main(String[] args) throws Exception{
        CountDownLatch latch = new CountDownLatch(1);
        ClientManager client = ClientManager.createClient();
        try{
            client.connectToServer(ClientEcho.class, new URI("ws://localhost:8025/websockets/echo"));
            latch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
