package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    public final static int SERVICE_PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress IPAddress = InetAddress.getByName(HOST);
            byte[] receivingDataBuffer = new byte[1024];
            byte[] sendingDataBuffer;

            String sentence = "Hello from UDP client";
            sendingDataBuffer = sentence.getBytes();
            DatagramPacket sendingPacket = new DatagramPacket(
                    sendingDataBuffer,
                    sendingDataBuffer.length,
                    IPAddress,
                    SERVICE_PORT
            );
            clientSocket.send(sendingPacket);

            DatagramPacket receivingPacket = new DatagramPacket(
                    receivingDataBuffer,
                    receivingDataBuffer.length
            );
            clientSocket.receive(receivingPacket);
            String receivedData = new String(receivingPacket.getData());
            System.out.println(receivedData);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}