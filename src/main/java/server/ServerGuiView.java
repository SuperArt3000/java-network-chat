package server;

import database.SQLService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Zurbaevi Nika
 */
public class ServerGuiView extends JFrame {
    private final ServerGuiController server;

    private JButton buttonStartServer;
    private JButton buttonStopServer;
    private JLabel labelPort;
    private JPanel panelConfiguration;
    private JPanel panelMain;
    private JPanel panelTextArea;
    private JScrollPane scrollPanelTextArea;
    private JTextArea textAreaLog;
    private JTextField textFieldInputPort;
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem menuItemLoadLog;
    private JMenuItem menuItemSaveLog;
    private JCheckBoxMenuItem checkBoxItemAlwaysOnTop;
    private JMenu menuOthers;

    public ServerGuiView(ServerGuiController server) {
        this.server = server;
    }

    protected void initComponents() {
        panelMain = new JPanel();
        panelTextArea = new JPanel();
        scrollPanelTextArea = new JScrollPane();
        textAreaLog = new JTextArea();
        panelConfiguration = new JPanel();
        labelPort = new JLabel();
        textFieldInputPort = new JTextField();
        buttonStartServer = new JButton();
        buttonStopServer = new JButton();
        menuBar = new JMenuBar();
        menu = new JMenu();
        menuItemSaveLog = new JMenuItem();
        menuItemLoadLog = new JMenuItem();
        menuOthers = new JMenu();
        checkBoxItemAlwaysOnTop = new JCheckBoxMenuItem();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setMinimumSize(new Dimension(800, 200));
        setPreferredSize(new Dimension(800, 200));
        setLocationRelativeTo(null);
        panelMain.setLayout(new BorderLayout());
        getContentPane().add(panelMain, BorderLayout.PAGE_END);
        panelTextArea.setLayout(new BorderLayout());
        textFieldInputPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        server.startServer(Integer.parseInt(textFieldInputPort.getText()));
                    } catch (Exception exception) {
                        refreshDialogWindowServer("Incorrect server port entered.\n");
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SQLService.disconnect();
                System.exit(0);
            }
        });

        textAreaLog.setEditable(false);
        textAreaLog.setColumns(20);
        textAreaLog.setRows(5);
        scrollPanelTextArea.setViewportView(textAreaLog);
        panelTextArea.add(scrollPanelTextArea, BorderLayout.CENTER);
        panelConfiguration.setLayout(new BoxLayout(panelConfiguration, BoxLayout.LINE_AXIS));
        labelPort.setText("Port:");
        panelConfiguration.add(labelPort);
        panelConfiguration.add(textFieldInputPort);
        buttonStartServer.setText("Start server");
        buttonStartServer.setMargin(new Insets(2, 80, 2, 80));
        buttonStartServer.addActionListener(e -> {
            try {
                server.startServer(Integer.parseInt(textFieldInputPort.getText()));
                textFieldInputPort.setText("");
            } catch (Exception exception) {
                refreshDialogWindowServer("Incorrect server port entered.\n");
            }
        });
        panelConfiguration.add(buttonStartServer);
        buttonStopServer.setText("Stop server");
        buttonStopServer.setMargin(new Insets(2, 80, 2, 80));
        buttonStopServer.addActionListener(e -> {
            server.stopServer();
            textFieldInputPort.setText("");
        });
        panelConfiguration.add(buttonStopServer);
        panelTextArea.add(panelConfiguration, BorderLayout.PAGE_END);
        getContentPane().add(panelTextArea, BorderLayout.CENTER);
        menu.setText("File");
        menuItemSaveLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItemSaveLog.setText("Save log");
//        menuItemSaveLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/SAVE.png"))));
        menuItemSaveLog.addActionListener(e -> saveToFile());
        menu.add(menuItemSaveLog);
        menuItemLoadLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItemLoadLog.setText("Load log");
//        menuItemLoadLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/OPEN.png"))));
        menuItemLoadLog.setToolTipText("");
        menuItemLoadLog.addActionListener(e -> openToFile());
        menu.add(menuItemLoadLog);
        menuBar.add(menu);
        menuOthers.setText("Others");
        checkBoxItemAlwaysOnTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        checkBoxItemAlwaysOnTop.setSelected(false);
        checkBoxItemAlwaysOnTop.setText("Always on top");
        checkBoxItemAlwaysOnTop.addActionListener(e -> {
            if (checkBoxItemAlwaysOnTop.getState()) {
                checkBoxItemAlwaysOnTop.setSelected(true);
                setAlwaysOnTop(true);
            } else {
                checkBoxItemAlwaysOnTop.setSelected(false);
                setAlwaysOnTop(false);
            }
        });
        menuOthers.add(checkBoxItemAlwaysOnTop);
        menuBar.add(menuOthers);
        setJMenuBar(menuBar);
        setVisible(true);
        pack();
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

    private void openToFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(new JButton("Open")) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Scanner scanner = new Scanner(new FileInputStream(file));
                while (scanner.hasNext()) {
                    textAreaLog.append(scanner.nextLine() + "\n");
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
