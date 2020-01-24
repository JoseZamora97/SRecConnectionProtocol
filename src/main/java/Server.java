import java.io.File;

import SRecProtocol.Server.SRecServer;

public class Server {

    static int PORT = 0;

    public static void main(String[] args) {
        SRecServer server = new SRecServer(PORT);
        server.startService();
    }

}
