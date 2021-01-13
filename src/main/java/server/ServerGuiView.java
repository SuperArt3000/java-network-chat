package server;

import client.ClientGuiView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Zurbaevi Nika
 */
public class ServerGuiView extends JFrame {
    private final ServerGuiController server;

    private JButton buttonStartServer;
    private JButton buttonStopServer;
    private JScrollPane scrollPanel;
    private JTextArea textAreaLog;
    private JButton buttonSaveLog;

    public ServerGuiView(ServerGuiController server) {
        this.server = server;

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGuiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    protected void initComponents() {
        buttonStartServer = new JButton();
        buttonStopServer = new JButton();
        scrollPanel = new JScrollPane();
        textAreaLog = new JTextArea();
        buttonSaveLog = new JButton();

        setTitle("Server");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 300));
        setPreferredSize(new java.awt.Dimension(500, 300));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
                System.exit(0);
            }
        });

        try {
            setIconImage(ImageIO.read(new File("default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonStartServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/start-server.png"))));
        buttonStartServer.setText("Start server");
        buttonStartServer.addActionListener(e -> server.startServer(getPortFromOptionPane()));

        buttonStopServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/stop-server.png"))));
        buttonStopServer.setText("Stop server");
        buttonStopServer.addActionListener(e -> server.stopServer());

        buttonSaveLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/save-log.png"))));
        buttonSaveLog.setText("Save log");
        buttonSaveLog.addActionListener(e -> saveToFile());

        textAreaLog.setEditable(false);
        textAreaLog.setColumns(20);
        textAreaLog.setRows(5);
        scrollPanel.setViewportView(textAreaLog);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonStartServer, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonStopServer, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSaveLog, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                                        .addComponent(scrollPanel))
                                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonStartServer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonStopServer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonSaveLog, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5))
        );

        pack();
        setVisible(true);
    }

    public void refreshDialogWindowServer(String serviceMessage) {
        textAreaLog.append(serviceMessage);
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(new JButton("Save")) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getParentFile(), file.getName() + ".txt");
                }
                try {
                    textAreaLog.write(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected int getPortFromOptionPane() {
        while (true) {
            String port = JOptionPane.showInputDialog(this, "Enter the server port:", "Server port input", JOptionPane.QUESTION_MESSAGE);
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this, "Incorrect server port entered. Try again.", "Server port input error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
