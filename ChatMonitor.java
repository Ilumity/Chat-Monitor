package com.lumity.chatmonitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

public class ChatMonitor extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextArea chatArea;

	private DatagramSocket socket;

	private Thread readThread;

	public ChatMonitor() {
		setTitle("Chat Monitor");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		setSize(screenWidth / 2, screenHeight / 2);
		setLocationRelativeTo(null);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane, BorderLayout.CENTER);

		ImageIcon backgroundIcon = new ImageIcon(ChatMonitor.class.getResource("/com/lumity/chatmonitor/img5.jpg"));
		JLabel background = new JLabel("");
		background = new JLabel(backgroundIcon);
		background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
		layeredPane.add(background);

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(new Color(192, 192, 192));
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		chatArea.setBounds(10, 10, (int) (screenWidth / 2 - 35), (int) (screenHeight / 2 - 58));

		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setBounds(10, 10, (int) (screenWidth / 2 - 35), (int) (screenHeight / 2 - 58));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		layeredPane.add(scrollPane);
		setVisible(true);
	}

	public void connect(String host, int port) throws IOException {
		socket = new DatagramSocket(port, InetAddress.getByName(host));
	}

	public void start() {
		readThread = new Thread(new Runnable() {
			public void run() {
				ChatMonitor.this.run();
			}
		});
		readThread.start();
	}

	public void stop() throws IOException {
		readThread.interrupt();
		socket.close();
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String message = new String(packet.getData(), 0, packet.getLength());
				chatArea.append(message + "\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		File file = new File("config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ChatMonitor chatMonitor = new ChatMonitor();
		try {
			chatMonitor.connect(config.getString("ServerAddress"), config.getInt("Port"));
			chatMonitor.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
