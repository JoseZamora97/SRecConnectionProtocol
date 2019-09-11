package TcpSRecProtocol;

/**
 * Codes from SRec Connection Protocol.
 */
public class SRecProtocolCodes {

    /**
     * CODE: HII, ( client -> server )
     * when the server get this CODE saves in
     * aliveConnections list from ServerConnectionService class.
     * and say to client if is conected or not.
     */
    public static final byte HII = 0;

    /**
     * CODE: PUT, ( client -> server )
     * when the server get this CODE take
     * info from InputStream.
     * And create a file.
     *
     * The message has the next codification:
     *
     *  |__CODE__| |__NAME_SIZE__| |__NAME__| |__CONTENT__|
     *    1 byte       1 byte         ...          ...
     */
    public static final byte PUT = 1;

    /**
     * CODE: BYE, ( client -> server )
     * when the server get this CODE removes the client
     * information from aliveConnections list from ServerConnectionService class.
     */
    public static final byte BYE = 2;

    /**
     * CODE: OKK, ( server -> client )
     * when server indicate that operation was successful.
     */
    public static final byte OKK = -1;

    /**
     * CODE: BAD, ( server -> client )
     * when server indicate that operation was not successful.
     */
    public static final byte BAD = -2;
}
