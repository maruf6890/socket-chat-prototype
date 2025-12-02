import java.io.*;
import java.net.*;

public class ChatServer extends Thread {
    private int port = 5000;

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Chat server started…");

            while (true) {
                Socket socket = server.accept();
                new ChatHandler(socket).start();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    class ChatHandler extends Thread {
        private Socket socket;

        ChatHandler(Socket socket) { this.socket = socket; }

        public void run() {
            try {
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);

                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Received: " + msg);
                    out.println(msg);
                }

                socket.close();

            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
