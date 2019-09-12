package TcpSRecProtocol;

import java.io.File;

/**
 * @author Jose Miguel Zamora Batista.
 * SRecMessage implementation,
 * This class is used for send messages
 * from client to server.
 */
public class SRecMessageRequest extends SRecMessage {

    /* File to send */
    private final File file;

    /**
     * Constructor of a request.
     * @param code the code of the new request
     * @param file the file to send. Can be Null.
     */
    public SRecMessageRequest(byte code, File file) {
        super(code);
        this.file = file;
    }

    /**
     * Return the file.
     * @return the file.
     */
    public File getFile() {
        return file;
    }

}
