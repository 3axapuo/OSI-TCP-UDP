package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public final static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            while (true) {
                byte[] receivingDataBuffer = new byte[1024];
                byte[] sendingDataBuffer;

                DatagramPacket inputPacket = new DatagramPacket(
                        receivingDataBuffer,
                        receivingDataBuffer.length
                );
                System.out.println("Waiting for a client to connect...");
                serverSocket.receive(inputPacket);
                String receivedData = new String(inputPacket.getData());
                System.out.println("Sent from the client: " + receivedData);

                InetAddress senderAddress = inputPacket.getAddress();
                int senderPort = inputPacket.getPort();
                sendingDataBuffer = String
                        .format("Соединение установлено с портом %s", senderPort)
                        .getBytes();
                DatagramPacket outputPacket = new DatagramPacket(
                        sendingDataBuffer,
                        sendingDataBuffer.length,
                        senderAddress,
                        senderPort
                );
                serverSocket.send(outputPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
