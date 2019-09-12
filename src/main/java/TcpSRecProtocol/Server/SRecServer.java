package TcpSRecProtocol.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SRecServer implements Server{

    private ServerSocket serverSocket;

    public SRecServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port, 1, InetAddress.getLocalHost());
        System.out.println("Server creado en: " + InetAddress.getLocalHost() + ":" + this.serverSocket.getLocalPort());
    }

    @Override
    public void startService() {
        ServerConnectionService serverConnectionService = new ServerConnectionService(serverSocket);
        serverConnectionService.run();
    }
}
