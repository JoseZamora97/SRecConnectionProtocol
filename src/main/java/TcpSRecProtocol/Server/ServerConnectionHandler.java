package TcpSRecProtocol.Server;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import TcpSRecProtocol.SRecProtocolCodes;

public class ServerConnectionHandler implements Runnable {

    private Socket client;
    private static final int BUFFER_SIZE = 1024;
    private final int MAX_CONNECTIONS;
    private byte REQUEST;

    ServerConnectionHandler(int connections, Socket client) {
        this.client = client;
        this.MAX_CONNECTIONS = connections;
    }

    @Override
    public void run() {
        try {
            byte[] message = getMessageBytes ();

            REQUEST = message[0];

            switch (REQUEST) {
                case SRecProtocolCodes.HII:
                    saveClient();
                    break;
                case SRecProtocolCodes.PUT:
                    receiveFile(message);
                    break;
                case SRecProtocolCodes.BYE:
                    deleteClient();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteClient() {
       ServerConnectionService.aliveConnections.remove(client.getInetAddress());
       response(SRecProtocolCodes.OKK);
    }

    private void receiveFile(byte[] message) throws IOException {
        byte nameSize = message[1];
        byte[] name = Arrays.copyOfRange(message, 1,  1 + nameSize + 1);
        byte[] content = Arrays.copyOfRange(message, 1 + nameSize + 1, message.length);

        File file = new File(new String(name, StandardCharsets.UTF_8));

        if(file.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            response(SRecProtocolCodes.OKK);
        }
        else
            response(SRecProtocolCodes.BAD);
    }

    private void saveClient() {
        if (ServerConnectionService.aliveConnections.size() < MAX_CONNECTIONS){
            ServerConnectionService.aliveConnections.addIfAbsent(client.getInetAddress());
            response(SRecProtocolCodes.OKK);
        }
        else
            response(SRecProtocolCodes.BAD);
    }

    private void response(byte serverCode) {

        String theResponse = "";

        switch (REQUEST) {
            case SRecProtocolCodes.HII:
                if (serverCode == SRecProtocolCodes.OKK)
                    theResponse = "OK!, Connected to SREC";
                else
                    theResponse = "Sorry!, Couldn't Connect to SREC. No available Slots.";
                break;
            case SRecProtocolCodes.PUT:
                if (serverCode == SRecProtocolCodes.OKK)
                    theResponse = "OK!, File was send successfully.";
                else
                    theResponse = "Sorry!, Couldn't receive the file.";
                break;
            case SRecProtocolCodes.BYE:
                if (serverCode == SRecProtocolCodes.OKK)
                    theResponse = "OK!, Good Bye.";
                break;
        }

        try {

            OutputStream outputStream = client.getOutputStream();
            byte[] theResponseBytes = theResponse.getBytes(StandardCharsets.UTF_8);

            outputStream.write(new byte[]{serverCode}, 0, 1);
            outputStream.write(theResponseBytes, 0, theResponseBytes.length);
            outputStream.flush();

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getMessageBytes () throws IOException {
        InputStream inputStream = client.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;

        byte[] data = new byte[BUFFER_SIZE];

        do{
            nRead = inputStream.read(data, 0, BUFFER_SIZE);
            buffer.write(data, 0, nRead);
        } while(nRead != -1);

        return buffer.toByteArray();
    }
}
