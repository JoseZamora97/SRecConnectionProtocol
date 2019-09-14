package TcpSRecProtocol.Server;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;

import TcpSRecProtocol.Client.Client;
import TcpSRecProtocol.SRecMessage;
import TcpSRecProtocol.SRecMessageRequest;
import TcpSRecProtocol.SRecMessageResponse;

/**
 * @author Jose Miguel Zamora Batista.
 * SercerConnectionHandler is allways created by an
 * Executor service located in ServerConnectionService class
 * @see ServerConnectionService
 * This class handles with a new client,
 * receive his SRecMessageRequest request, process it and sent
 * to client a SRecMessageResponse response.
 * @see SRecMessageRequest
 * @see SRecMessageRequest
 */
class ServerConnectionHandler implements Runnable {

    /* New client socket */
    private Socket client;

    /**
     * Constructor that Creates the connection handler.
     * @param client new client.
     */
    ServerConnectionHandler(Socket client) {
        this.client = client;
    }

    /**
     * Start listening for a SRecMessageRequest message sent by
     * the client and wait ( this block the thread until the
     * message is read) and then process that request and send
     * a new SRecMessageResponse response to the client.
     * @see SRecMessageRequest
     * @see SRecMessageResponse
     */
    @Override
    public void run() {
        try {

            System.out.println("(Handler " + client.getLocalAddress() +") connected !");

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            SRecMessageRequest request = (SRecMessageRequest) ois.readObject();

            logConsole(request);

            SRecMessageResponse response = attendRequest(request);

            logConsole(response);

            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(response);
            oos.flush();

        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private SRecMessageResponse attendRequest(SRecMessageRequest request) {
        switch (request.getCode()) {
            case Client.HII:
                return registerNewClientResponse();
            case Client.PUT:
                return receiveFileResponse(request.getFile());
            case Client.BYE:
                return deleteClientResponse();
        }

        // If reaches here something was wrong.
        return new SRecMessageResponse(Server.BAD);
    }

    /**
     * Register a new client into ServerConnectionService list.
     * @return status response.
     */
    private SRecMessageResponse registerNewClientResponse() {
        ServerConnectionService.aliveConnections.addIfAbsent(client.getInetAddress());
        return new SRecMessageResponse(Server.OKK);
    }

    /**
     * Delete a client from ServerConnectionService list.
     * @return status response.
     */
    private SRecMessageResponse deleteClientResponse() {
        ServerConnectionService.aliveConnections.remove(client.getInetAddress());
        return new SRecMessageResponse(Server.OKK);
    }

    /**
     * Receives the file sent by client..
     * @return status response.
     */
    private SRecMessageResponse receiveFileResponse(File file)  {

        SRecMessageResponse response = new SRecMessageResponse(Server.OKK);
        File fileOut = new File("fileReceived.txt");

        try {
            Files.copy(file.toPath(), fileOut.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            response = new SRecMessageResponse(Server.BAD);
        }

        return response;
    }

    /**
     * Print in console relevant information.
     * Just for debug.
     * @param message the message
     */
    private void logConsole(SRecMessage message) {

        if ( message instanceof SRecMessageRequest )
            System.out.println("(Handler " + client.getLocalAddress() +") request a message with code: " + message.getCode() + "!");
        else
            System.out.println("(Client: " + client.getLocalAddress() +") sending a response with code: " + message.getCode() + "!");
    }
}
