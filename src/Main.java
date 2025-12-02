import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main extends JFrame {

    private DefaultListModel<User> listModel;

    public Main() {
        setTitle("Java Socket Chat");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        listModel = new DefaultListModel<>();

        JList<User> userList = new JList<>(listModel);
        JButton searchBtn = new JButton("Search Users");

        searchBtn.addActionListener(e -> {
            listModel.clear();
            List<User> found = DiscoveryService.searchUsers();
            for (User u : found) listModel.addElement(u);
        });

        userList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    User selected = userList.getSelectedValue();
                    new ChatScreen(selected.getIp_address());
                }
            }
        });

        setLayout(new BorderLayout());
        add(searchBtn, BorderLayout.NORTH);
        add(new JScrollPane(userList), BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Start TCP server
        new ChatServer().start();

        // Start UI
        new Main();
    }
}
