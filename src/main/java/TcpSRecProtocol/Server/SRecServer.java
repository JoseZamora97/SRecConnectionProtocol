package TcpSRecProtocol.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * @author Jose Miguel Zamora Batista.
 * Implementation of Client Interface.
 * @see Server
 * SRecServer uses a ServerConnectionServicers in
 * another thread for handling new connections.
 * @see ServerConnectionService
 */
public class SRecServer implements Server{

    /* The server socket */
    private ServerSocket serverSocket;

    /**
     * SRecServer Constructor.
     * Creates a server with the machine InetAddress.
     * @param port the port for creating the new server.
     */
    public SRecServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port, 1, InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overrides interface's startService method.
     * Creates a ServerConnectionService object for handling the
     * new connections.
     * @see ServerConnectionService
     */
    @Override
    public void startService() {
        ServerConnectionService serverConnectionService = new ServerConnectionService(serverSocket);
        serverConnectionService.run();
    }
}
