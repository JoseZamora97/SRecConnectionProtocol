package SRecProtocol.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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
@SuppressWarnings("unused")
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

    /**
     * Getter of isServiceAvailable.
     * @return true if is this service Available
     */
    public boolean isServiceAvailable() {
        return isServiceAvailable;
    }

    /**
     * Setter of isServiceAvailable.
     * @param serviceAvailable true or false.
     */
    public void setServiceAvailable(boolean serviceAvailable) {
        isServiceAvailable = serviceAvailable;
    }

    /**
     * Getter of Alive Connections List
     * @return alive connections list.
     */
    public ObservableList<InetAddress> getAliveConnections() {
        return aliveConnections;
    }

    /**
     * Setter of Output folder.
     * Pre: Must be a valid location.
     * @param outputFolder new path.
     */
    public void setOutputFolder(String outputFolder) {
        synchronized (this){
            this.outputFolder = outputFolder;
        }
    }

    /**
     * Getter of Output folder.
     * @return the current output folder.
     */
    public String getOutputFolder() {
        return this.outputFolder;
    }

}
