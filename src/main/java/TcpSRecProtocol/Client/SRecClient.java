package TcpSRecProtocol.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import TcpSRecProtocol.SRecMessageRequest;
import TcpSRecProtocol.SRecMessageResponse;
import TcpSRecProtocol.Server.Server;



/**
 * @author Jose Miguel Zamora Batista.
 * Implementation of Client Interface.
 * @see Client
 * SRecClient interacts directly with SRecServer
 * @see TcpSRecProtocol.Server.SRecServer
 */
public class SRecClient implements Client {

    /* Client socket */
    private Socket socket;

    /* Response last operation Result */
    private volatile boolean allWasGood;

    /**
     * SRecClient Constructor.
     * @param host the ip to connect.
     * @param port the port where to send requests.
     */
    public SRecClient(String host, String port) {
        try {
            this.socket = new Socket(host, Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overrides interface's send method.
     * This create a new thread to handle he request
     * and send it.
     * Firstly, a SRecMessageRequest message is sent to server.
     * Secondly, server send a SRecMessageResponse message.
     * and update allWasGood variable.
     * @param request the SRecMessageRequest message sent to server
     * @see SRecMessageRequest
     * @see SRecMessageResponse
     */
    @Override
    public void send(final SRecMessageRequest request){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    allWasGood = true;

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(request);
                    oos.flush();

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    SRecMessageResponse response = (SRecMessageResponse) ois.readObject();

                    if (response.getCode() == Server.BAD )
                        allWasGood = false;

                    socket.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    allWasGood = false;
                }
            }
        }).run();
    }

    /**
     * Overrides interface's getLastResponseResult method.
     * If last request was good return true, else false.
     * @return allWasGood.
     */
    @Override
    public synchronized boolean getLastResponseResult() {
        return allWasGood;
    }
}
