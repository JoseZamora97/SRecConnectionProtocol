package TcpSRecProtocol.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SRecServer {

    private ServerSocket serverSocket;

    public SRecServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port, 1, InetAddress.getLocalHost());
    }

    public void startService(int connections) {
        ServerConnectionService serverConnectionService = new ServerConnectionService(serverSocket, connections);
        serverConnectionService.run();
    }
}
