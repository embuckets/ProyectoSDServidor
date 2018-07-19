/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emilio
 */
public class Protocol implements AutoCloseable {

    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public Protocol(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new ObjectInputStream(socket.getInputStream());
        this.writer = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(RemoteMessage type, Object argument) throws IOException {
        writer.writeObject(type);
        writer.writeObject(argument);
        writer.writeInt('\0');
        writer.flush();
    }

    public Pair<RemoteMessage, Object> receiveMessage() throws IOException {
        try {
            RemoteMessage message = (RemoteMessage) reader.readObject();
            Object arg = reader.readObject();
            int endOfMessage = reader.readInt();
            if (endOfMessage != '\0') {
                throw new IOException("Mensaje no esta bien estructurado");
            }
            return new Pair<>(message, arg);
        } catch (ClassNotFoundException ex) {
            throw new IOException("No se pudo deserializar el mensaje");
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException ex) {
        }
        try {
            writer.close();
        } catch (IOException ex) {
        }
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }
}
