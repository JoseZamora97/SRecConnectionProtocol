package TcpSRecProtocol.Server;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;

import TcpSRecProtocol.Client.Client;
import TcpSRecProtocol.SRecMessageRequest;
import TcpSRecProtocol.SRecMessageResponse;

public class ServerConnectionHandler implements Runnable {

    private Socket client;

    ServerConnectionHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            SRecMessageRequest request = (SRecMessageRequest) ois.readObject();

            SRecMessageResponse response = attendRequest(request);

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

    private SRecMessageResponse registerNewClientResponse() {
        ServerConnectionService.aliveConnections.addIfAbsent(client.getInetAddress());
        return new SRecMessageResponse(Server.OKK);
    }

    private SRecMessageResponse deleteClientResponse() {
        ServerConnectionService.aliveConnections.remove(client.getInetAddress());
        return new SRecMessageResponse(Server.OKK);
    }

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
}
