package com.lumity.chatmonitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

public class ChatMonitor {

	private JFrame frame;
	private JTextArea chatArea;
	private DatagramSocket socket;
	private Thread readThread;
	private MessageReceiver msg;
	private Buttons buttons;
	static ChatMonitor chatMonitor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File file = new File("config.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
					chatMonitor = new ChatMonitor();
					chatMonitor.frame.setVisible(true);
					chatMonitor.connect(config.getString("ServerAddress"), config.getInt("Port"));
					chatMonitor.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChatMonitor() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("Chat Monitor");

		ImageIcon iconImage = new ImageIcon(ChatMonitor.class.getResource("/com/lumity/chatmonitor/axe.png"));
		frame.setIconImage(iconImage.getImage());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		frame.setSize(960, 562);
		frame.setLocationRelativeTo(null);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(new Color(51, 51, 51));
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(new Color(192, 192, 192));
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		chatArea.setBounds(10, 10, (int) (screenWidth / 2 - 35), (int) (screenHeight / 2 - 58));

		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollbarUI());
		scrollPane.setPreferredSize(new Dimension(200, 200));
		scrollPane.setBounds(10, 10, (int) (screenWidth / 2 - 35), (int) (screenHeight / 2 - 58));
		layeredPane.add(scrollPane);

		buttons = new Buttons();
		msg = new MessageReceiver(buttons);

		JButton chatButton = new CustomButton("Chat");
		chatButton.setBounds(335, 496, 89, 23);
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttons.toggleChat();
			}
		});
		layeredPane.add(chatButton);

		JButton cmdButton = new CustomButton("Commands");
		cmdButton.setBounds(434, 496, 108, 23);
		cmdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttons.toggleCommands();
			}
		});
		layeredPane.add(cmdButton);

		JButton bothButton = new CustomButton("Both");
		bothButton.setBounds(552, 496, 89, 23);
		bothButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttons.toggleBoth();
			}
		});
		layeredPane.add(bothButton);
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
				msg.displayMessage(message);
				chatArea.append(message + "\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
