import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FileClient {

    private static final String key = "jinxtwitchcaitlyn";

    private final String hostName;
    private final int port;
    private Socket socket;
    private OutputStream out;
    private BufferedInputStream in;

    public FileClient(String hostName, int port) throws InterruptedException {
        this.hostName = hostName;
        this.port = port;

        this.connect();
    }

    private void connect() throws InterruptedException {
        while (true) {
            this.socket = new Socket();

            try {
                this.socket.setSoTimeout(10000);
                this.socket.connect(new InetSocketAddress(this.hostName, this.port), 10000);

                this.out = new BufferedOutputStream(socket.getOutputStream());
                this.in = new BufferedInputStream(socket.getInputStream());

                break;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Retrying in 10 seconds");

                try {
                    this.socket.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }

                Thread.sleep(10000);
            }
        }
    }

    // soo soo bad, whatever
    public boolean uploadJar(String jarName) throws IOException {
        File jarFile = new File(jarName + ".jar");
        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(jarFile));
        byte[] nameBytes = jarName.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + nameBytes.length + (int) jarFile.length());

        buffer.putInt(nameBytes.length).put(nameBytes).put(this.read(fileInput, (int) jarFile.length()));

        return this.sendAndReceiveResponse("uploadjar", buffer.array()).equals("success");
    }

    public void endConnection() throws IOException {
        this.socket.close();
    }

    private void send(String command, byte[] data) throws IOException {
        byte[] keyBytes = FileClient.key.getBytes();
        byte[] commandBytes = command.getBytes();
        byte[] commandLenBytes = ByteBuffer.allocate(4).putInt(commandBytes.length).array();
        byte[] packetHeader = ByteBuffer.allocate(
            keyBytes.length + commandLenBytes.length + commandBytes.length
        ).put(keyBytes).put(commandLenBytes).put(commandBytes).array();
        byte[] packetLen = ByteBuffer.allocate(4).putInt(packetHeader.length + data.length).array();

        this.out.write(packetLen);
        this.out.write(packetHeader);
        this.out.write(data);
        this.out.flush();
    }

    private String sendAndReceiveResponse(String command, byte[] data) throws IOException {
        this.send(command, data);

        return new BufferedReader(new InputStreamReader(this.in)).readLine();
    }

    private byte[] sendAndReceive(String command, byte[] data) throws IOException {
        this.send(command, data);

        return this.readDataPacket();
    }

    private byte[] readDataPacket() throws IOException {
        int size = this.readInt();

        return this.read(size);
    }

    private int readInt() throws IOException {
        byte[] intBytes = this.read(4);

        return this.intFromBytes(intBytes);
    }

    private int intFromBytes(byte[] pl) {
        return pl[3] & 0xFF | (pl[2] & 0xFF) << 8 | (pl[1] & 0xFF) << 16 | (pl[0] & 0xFF) << 24;
    }

    private byte[] read(int length) throws IOException {
        return this.read(this.in, length);
    }

    private byte[] read(InputStream input, int length) throws IOException {
        byte[] pl = new byte[length];
        int plRead = 0;

        while (plRead < pl.length) {
            int numRead = input.read(pl, plRead, pl.length - plRead);

            if (numRead == -1) throw new IOException();

            plRead += numRead;
        }

        return pl;
    }

//    private boolean read(InputStream input, byte[] data, int start, int length) throws IOException {
//        int dataRead = 0;
//
//        while (dataRead < length) {
//            int numRead = input.read(data, start + dataRead, length - dataRead);
//
//            if (numRead == -1) return true;
//
//            dataRead += numRead;
//        }
//
//        return false;
//    }

}
