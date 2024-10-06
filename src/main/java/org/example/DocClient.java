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

public class DocClient{
    public static void main(String[] args) throws Exception{
        ClientManager client = ClientManager.createClient();
        while(true){
            client.connectToServer(ClientEcho.class, new URI("ws://192.168.0.106:8025/websockets/echo"));
        }
    }
}