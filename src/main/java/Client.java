import SRecProtocol.Client.SRecClient;
import SRecProtocol.Messages.SRecMessageRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Client{

    private static String IP = "127.0.0.1";
    private static int PORT = 55555;

    public static void main (String[] args) throws InterruptedException {
        SRecClient client1, client2, client3;

        client1 = new SRecClient(IP, String.valueOf(PORT));

        SRecMessageRequest request1 = new SRecMessageRequest(SRecProtocol.Client.Client.HII,
                null, null);

        client1.send(request1);

        Thread.sleep(3000);

        //========================================================================

        client1 = new SRecClient(IP, String.valueOf(PORT));

        File file = new File("C:\\Users\\jose.zamora.batista\\IdeaProjects\\SRecConnectionProtocol\\src\\main\\java\\input\\holi.txt");

        byte[] array;

        try {
            array = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            array = new byte[0];
        }

        SRecMessageRequest request3 = new SRecMessageRequest(SRecProtocol.Client.Client.PUT, file.getName(), array);

        client1.send(request3);

        //========================================================================

        client1 = new SRecClient(IP, String.valueOf(PORT));

        SRecMessageRequest request2 = new SRecMessageRequest(SRecProtocol.Client.Client.BYE,
                null, null);

        client1.send(request2);
    }
}