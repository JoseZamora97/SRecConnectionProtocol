package TcpSRecProtocol.Server;

public interface Server {

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

    void startService();

}
