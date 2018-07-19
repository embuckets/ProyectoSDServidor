/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.central;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import remote.shared.MessageHandler;
import remote.shared.Pair;
import remote.shared.Protocol;
import remote.shared.RemoteMessage;

/**
 *
 * @author emilio
 */
public class ClientHandler implements Runnable{

    private Socket socket;
    private boolean listening;
//    private BufferedReader reader;
//    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        listening = true;
    }

    @Override
    public void run() {
        try (Protocol protocol = new Protocol(socket);) {
            String mensaje = "A que tipo de servidor te quieres conectar?\n"
                    + "[rmi, rpc, socket]\n";
            protocol.sendMessage(RemoteMessage.RESPONSE_OK, mensaje);
            while (listening) {
                Pair<RemoteMessage, Object> response = protocol.receiveMessage();
                try {
                    handleMessage(protocol, response.getKey(), response.getValue());

                } catch (IllegalArgumentException e) {
                    protocol.sendMessage(RemoteMessage.RESPONSE_ERROR, e.getMessage());
                } catch (IOException e) {
                    //termina ejecucion
                }
            }

        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);

    }

    private void handleMessage(Protocol protocol, RemoteMessage key, Object value) throws IllegalArgumentException, IOException {
        switch (key) {
            case REQUEST_CONNECTION:
                if (value instanceof String) {
                    //try
                    //startProxyOfType(value)
                    listening = false;
                } else {
                    throw new IllegalArgumentException("No existe un servidor para " + String.valueOf(value));
                }
                break;
            case REQUEST_END:
                this.listening = false;
                protocol.sendMessage(RemoteMessage.RESPONSE_END, null);
                break;
            default:
                throw new IllegalArgumentException("Operacion " + String.valueOf(key) + " no es soportada");
        }
    }

}
