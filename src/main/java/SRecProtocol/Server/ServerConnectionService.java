package SRecProtocol.Server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Jose Miguel Zamora Batista.
 * ServerConnectionService never run in the same thread that
 * SRecServer does.
 * @see SRecServer
 * It has a list off connections and
 * Executor service creating new threads for deal with
 * new clients using ServerConnectionHandler objects.
 * @see ServerConnectionHandler
 */
public class ServerConnectionService implements Runnable {

    /* Thread Pool */
    private ExecutorService pool;

    /* The server socket */
    private ServerSocket serverSocket;

    /* Service Control */
    private volatile boolean isServiceAvailable;

    /* List of alive connections */
    private ObservableList<InetAddress> aliveConnections;

    private volatile String outputFolder;

    /**
     * Constructor function.
     * @param serverSocket server passed from SRecServer
     * @see SRecServer
     */
    ServerConnectionService(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = Executors.newCachedThreadPool();
        this.isServiceAvailable = true;
        this.aliveConnections = FXCollections.synchronizedObservableList(
                FXCollections.observableList(new LinkedList<>()));
    }

    /**
     * Start the service while isServiceAvailable is true.
     */
    @Override
    public void run() {

        while (isServiceAvailable) {
            try {
                this.pool.execute(new ServerConnectionHandler(serverSocket.accept(), this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.pool.shutdown();
    }

    public boolean isServiceAvailable() {
        return isServiceAvailable;
    }

    public void setServiceAvailable(boolean serviceAvailable) {
        isServiceAvailable = serviceAvailable;
    }

    public ObservableList<InetAddress> getAliveConnections() {
        return aliveConnections;
    }

    public void setOutputFolder(String outputFolder) {
        synchronized (this){
            this.outputFolder = outputFolder;
        }
    }

    public String getOutputFolder() {
        return this.outputFolder;
    }

}
