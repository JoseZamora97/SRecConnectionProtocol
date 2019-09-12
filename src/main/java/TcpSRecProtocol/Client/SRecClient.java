package TcpSRecProtocol.Client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import TcpSRecProtocol.SRecMessageRequest;
import TcpSRecProtocol.SRecMessageResponse;


public class SRecClient implements Client {

    private Socket socket;

    public SRecClient(String host, String port) throws IOException {
        this.socket = new Socket(host, Integer.parseInt(port));
    }

    @Override
    public void send(SRecMessageRequest request){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(request);
                    oos.flush();

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    SRecMessageResponse response = (SRecMessageResponse) ois.readObject();

                    System.out.println("Echo code-> " + response.getCode());

                    socket.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }
}
