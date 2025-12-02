import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatScreen extends JFrame {
    private JTextArea chatArea;
    private JTextField input;
    private ChatClient client;

    public ChatScreen(String ip) {
        setTitle("Chat with " + ip);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        input = new JTextField();

        setLayout(new BorderLayout());
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(input, BorderLayout.SOUTH);

        try {
            client = new ChatClient(ip);

            // receiver thread
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = client.receiveMessage()) != null) {
                        chatArea.append("Them: " + msg + "\n");
                    }
                } catch (Exception ignored) {}
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to connect");
            dispose();
        }

        input.addActionListener(e -> {
            String msg = input.getText();
            client.sendMessage(msg);
            chatArea.append("Me: " + msg + "\n");
            input.setText("");
        });

        setVisible(true);
    }
}
