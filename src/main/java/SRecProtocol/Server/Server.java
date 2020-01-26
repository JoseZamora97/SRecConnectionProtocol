package SRecProtocol.Server;

/**
 * @author Jose Miguel Zamora Batista.
 * Interface Server,
 * Contains all codes that are sent from
 * server to clients.
 */
@SuppressWarnings("unused")
public interface Server {

    /**
     * CODE: OKK,
     * when server indicate that operation was successful.
     */
    byte OKK = 20;

    /**
     * CODE: BAD,
     * when server indicate that operation was not successful.
     */
    byte BAD = 33;

    /**
     * Start running the service.
     */
    void startService();

    /**
     * Stop the service.
     */
    void stopService();

    /**
     * Get Connections Details.
     * @return "ip"+":"+"port" string format.
     */
    String getConnectionDetails();
}
