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
public enum RemoteMessage {
    REQUEST_FILE,
    REQUEST_FILES_LIST,
    REQUEST_CONNECTION,
    REQUEST_END,
    RESPONSE_FILE_OK,
    RESPONSE_FILE_ERROR,
    RESPONSE_FILES_LIST_OK,
    RESPONSE_FILES_LIST_ERROR,
    RESPONSE_OK,
    RESPONSE_ERROR,
    RESPONSE_END
}
