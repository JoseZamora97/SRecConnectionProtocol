package TcpSRecProtocol.Client;

import java.io.File;

public interface Client {

    /**
     * CODE: HII,
     * when the server get this CODE saves in
     * aliveConnections list from ServerConnectionService class.
     * and say to client if is connected or not.
     */
    byte HII = 10;

    /**
     * CODE: PUT,
     * when the server get this CODE take
     * info from InputStream.
     * And create a file.
     */
    byte PUT = 13;

    /**
     * CODE: BYE,
     * when the server get this CODE removes the client
     * information from aliveConnections list from ServerConnectionService class.
     */
    byte BYE = 3;


    /**
     *
     * @param code
     * @param content
     */
    void send(final byte code, final File content);
}
