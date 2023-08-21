package net.itsrelizc.protocol.matchmaker.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import net.itsrelizc.networking.CommunicationInput;

public class ProxyServer {
	
	private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        
        while (true) {
        	clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            DataInputStream d = new DataInputStream(clientSocket.getInputStream());
            
            String inputLine;
         
            CommunicationInput inp = new CommunicationInput(d);
            a(inp);
        }
    }
    
    public void a(CommunicationInput inp) {
    	
    }
}
