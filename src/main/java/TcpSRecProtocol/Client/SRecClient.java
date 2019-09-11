package TcpSRecProtocol.Client;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import TcpSRecProtocol.SRecProtocolCodes;

public class SRecClient {

    private Socket socket; // Socket con el servidor.

    public SRecClient(String host, String port) {
        try {
            this.socket = new Socket(host, Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(final byte code) throws IOException {

        final OutputStream outputStream = socket.getOutputStream();

        new Runnable() {

            OutputStream runOutputStream = outputStream;

            @Override
            public void run() {
                try {
                    runOutputStream.write(new byte[]{code}, 0, 1);
                    runOutputStream.flush();
                    runOutputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
    }

    public void sendFile(final String name, final File file) throws IOException {

        final OutputStream outputStream = socket.getOutputStream();

        new Runnable() {

            OutputStream runOutputStream = outputStream;

            @Override
            public void run() {

                try {
                    byte nameSize = (byte) name.length();
                    RandomAccessFile f = new RandomAccessFile(file, "r");
                    byte[] content = new byte[(int) f.length()];
                    f.readFully(content);

                    runOutputStream.write(new byte[]{SRecProtocolCodes.PUT}, 0, 1);
                    runOutputStream.write(new byte[]{nameSize}, 0, 1) ;
                    runOutputStream.write(content, 0, content.length);

                    runOutputStream.flush();
                    runOutputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


    }

    public void close() throws IOException {
        this.socket.close();
    }
}
