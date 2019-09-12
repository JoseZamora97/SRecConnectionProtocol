package TcpSRecProtocol.Server;

/**
 * @author Jose Miguel Zamora Batista.
 * Interface Server,
 * Contains all codes that are sent from
 * server to clients.
 */
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

    /**
     * Start running the service.
     */
    void startService();

}
