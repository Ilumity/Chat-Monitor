package com.lumity.chatforwarder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatForwarder implements Listener {

	JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("ChatForwarder");

	private DatagramSocket socket;
	private InetAddress address;
	private int port = plugin.getConfig().getInt("Port");

	public ChatForwarder() {
		String host = plugin.getConfig().getString("ServerAddress");
		try {
			this.address = InetAddress.getByName(host);
			this.socket = new DatagramSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String message = event.getPlayer().getName() + ": " + event.getMessage();
		forwardMessage(message);
	}

	private void forwardMessage(String message) {
		byte[] data = message.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		socket.close();
	}

}