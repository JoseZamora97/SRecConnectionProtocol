package TcpSRecProtocol;

/**
 * Codes from SRec Connection Protocol.
 */
public interface SRecProtocolCodes {

    /**
     * CODE: HII, ( client -> server )
     * when the server get this CODE saves in
     * aliveConnections list from ServerConnectionService class.
     * and say to client if is connected or not.
     */
    byte HII = 10;

    /**
     * CODE: PUT, ( client -> server )
     * when the server get this CODE take
     * info from InputStream.
     * And create a file.
     */
    byte PUT = 13;

    /**
     * CODE: BYE, ( client -> server )
     * when the server get this CODE removes the client
     * information from aliveConnections list from ServerConnectionService class.
     */
    byte BYE = 3;

    /**
     * CODE: OKK, ( server -> client )
     * when server indicate that operation was successful.
     */
    byte OKK = 20;

    /**
     * CODE: BAD, ( server -> client )
     * when server indicate that operation was not successful.
     */
    byte BAD = 33;
}
