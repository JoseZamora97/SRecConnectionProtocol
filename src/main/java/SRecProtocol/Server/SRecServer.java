package SRecProtocol.Server;

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
    private ServerConnectionService serverConnectionService;
    private Thread serviceThread;


    /**
     * SRecServer Constructor.
     * Creates a server with the machine InetAddress.
     * @param port the port for creating the new server.
     */
    public SRecServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port, 1, InetAddress.getLocalHost());
            this.serverConnectionService = new ServerConnectionService(serverSocket);
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
        System.out.println("(Service) Server is running at: " + serverSocket.getInetAddress().getHostAddress() +
                ":" + serverSocket.getLocalPort());

        serviceThread = new Thread(this.serverConnectionService);
        serviceThread.start();
    }

    public void stopService() {
        System.out.println("(Service) Server is offline");
        this.serviceThread.interrupt();
    }

    @Override
    public String getConnectionDetails() {
        return this.serverSocket.getInetAddress().getHostAddress() + ":" + this.serverSocket.getLocalPort();
    }

    public ServerConnectionService getServerConnectionService() {
        return serverConnectionService;
    }

    public void setServerConnectionService(ServerConnectionService serverConnectionService) {
        this.serverConnectionService = serverConnectionService;
    }
}
