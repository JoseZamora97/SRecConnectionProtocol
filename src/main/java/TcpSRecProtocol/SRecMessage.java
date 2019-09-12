package TcpSRecProtocol;

import java.io.Serializable;

public abstract class SRecMessage implements Serializable {

    private final byte code;

    public SRecMessage (byte code) {
        this.code = code;
    }

    public byte getCode() {
        return this.code;
    }
}
