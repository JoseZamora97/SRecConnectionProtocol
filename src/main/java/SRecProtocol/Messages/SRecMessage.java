package SRecProtocol.Messages;

import java.io.Serializable;

/**
 * @author Jose Miguel Zamora Batista.
 * SRecMessage is a super class of
 * SRecMessageRequest and SRecMessageResponse
 */
public abstract class SRecMessage implements Serializable {

    /* Code */
    private final byte code;

    /**
     * Constructor.
     * @param code new message code.
     */
    public SRecMessage (byte code) {
        this.code = code;
    }

    /**
     * Get the message code.
     * @return the code.
     */
    public byte getCode() {
        return this.code;
    }
}
