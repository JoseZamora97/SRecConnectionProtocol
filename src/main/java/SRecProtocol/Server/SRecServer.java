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
@SuppressWarnings("unused")
public class SRecServer implements Server{

    /* The server socket */
    private ServerSocket serverSocket;

    /* The server service */
    private ServerConnectionService serverConnectionService;

    /* Thread of the service */
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
     * Launch a thread with the service object for handle
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


    /**
     * Overrides interface's stopService method.
     * Stop the thread by interrupting it.
     */
    @Override
    public void stopService() {
        System.out.println("(Service) Server is offline");
        this.serviceThread.interrupt();
    }

    /**
     * Overrides interface's getConnectionDetails method.
     * @return the ip:port of the server.
     */
    @Override
    public String getConnectionDetails() {
        return this.serverSocket.getInetAddress().getHostAddress() +
                ":" + this.serverSocket.getLocalPort();
    }

    /**
     * Getter method of the Server Service.
     * @return ServerConnectionService object.
     */
    public ServerConnectionService getServerConnectionService() {
        return serverConnectionService;
    }

    /**
     * Setter method of the Server Service.
     * @param serverConnectionService new serverConnectionService.
     */
    public void setServerConnectionService(ServerConnectionService serverConnectionService) {
        this.serverConnectionService = serverConnectionService;
    }
}
