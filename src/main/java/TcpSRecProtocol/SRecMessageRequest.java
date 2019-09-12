package TcpSRecProtocol;

import java.io.File;

public class SRecMessageRequest extends SRecMessage {

    private final File file;

    public SRecMessageRequest(byte code, File file) {
        super(code);
        this.file = file;
    }

    public File getFile() {
        return file;
    }

}
