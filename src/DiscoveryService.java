import java.net.*;
import java.util.*;

public class DiscoveryService {

    public static List<User> searchUsers() {
        List<User> users = new ArrayList<>();
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(1000);

            String message = "DISCOVER_CHAT_USER";
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length,
                    InetAddress.getByName("255.255.255.255"), 8888
            );

            socket.send(sendPacket);

            // listen for replies
            byte[] recvBuf = new byte[1500];
            long start = System.currentTimeMillis();

            while (System.currentTimeMillis() - start < 2000) {
                DatagramPacket receivePacket =
                        new DatagramPacket(recvBuf, recvBuf.length);

                try {
                    socket.receive(receivePacket);
                    String data = new String(receivePacket.getData()).trim();

                    if (data.startsWith("I_AM_HERE")) {
                        String[] parts = data.split(":");
                        String username = parts[1];
                        String ip = receivePacket.getAddress().getHostAddress();

                        users.add(new User(username, ip));
                    }

                } catch (Exception ignored) {}
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
