/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.central;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emilio
 */
public class Servidor {

    private static int port = 1111;

    public static void main(String[] args) {
        //start rmiServer()
        //start rpcServer()
        //start socketServer()
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            while(!Thread.interrupted()){
                Socket clientSocket = serverSocket.accept();
                //Handler handler = new Handler(clientSocket);
                //handler.start();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
    
    
}
