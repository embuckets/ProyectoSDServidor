/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import file.FileManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import remote.shared.Pair;
import remote.shared.Protocol;
import remote.shared.RemoteMessage;

/**
 *
 * @author emilio
 */
public class ProxyRPC implements Runnable {

    private Socket socket;
    private boolean listening;
    private XmlRpcClient client;
    public ProxyRPC(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Protocol protocol = new Protocol(socket)) {
            setUp();
            while (listening) {
                Pair<RemoteMessage, Object> input = protocol.receiveMessage();
                handleMessage(protocol, input.getKey(), input.getValue());
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(ProxyRPC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProxyRPC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProxyRPC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlRpcException ex) {
            Logger.getLogger(ProxyRPC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setUp() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
        config.setEnabledForExceptions(false);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);

        client = new XmlRpcClient();
        client.setConfig(config);

    }

    private void handleMessage(Protocol protocol, RemoteMessage type, Object value) throws XmlRpcException, IOException {
        switch (type) {
            case REQUEST_FILE:
                String param = (String) value;
                Object[] params = new Object[]{param};
                byte[] fileContents = (byte[]) client.execute("FileManager.getFileContents", params);
                protocol.sendMessage(RemoteMessage.RESPONSE_FILE_OK, fileContents);
                break;
            default:
                break;
        }
    }

}
