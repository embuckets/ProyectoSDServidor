/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.shared;

/**
 *
 * @author emilio
 */
public interface MessageHandler {
    void handleMessage(Protocol protocol, RemoteMessage key, Object value);
    
}
