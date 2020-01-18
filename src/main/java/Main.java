import java.io.File;

import SRecProtocol.Server.SRecServer;

public class Main {

    static int PORT = 55555;

    public static void main(String[] args) {

        SRecServer server = new SRecServer(PORT);
        server.startService();

    }

}
