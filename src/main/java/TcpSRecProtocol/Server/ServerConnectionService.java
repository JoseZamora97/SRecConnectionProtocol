package TcpSRecProtocol.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnectionService implements Runnable {

    private ExecutorService pool;
    private ServerSocket serverSocket;

    private volatile boolean isServiceAvailable;

    static CopyOnWriteArrayList<InetAddress> aliveConnections;

    ServerConnectionService(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;
        this.pool = Executors.newCachedThreadPool();

        this.isServiceAvailable = true;
        aliveConnections = new CopyOnWriteArrayList<>();
    }

    @Override
    public void run() {
        while (isServiceAvailable) {
            try {
                this.pool.execute(new ServerConnectionHandler(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.pool.shutdown();
    }

}
