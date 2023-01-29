package com.lumity.chatforwarder;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	ChatForwarder chatForwarder;

	@Override
	public void onEnable() {
		chatForwarder = new ChatForwarder();
		checkFile();

		registerEventListener(new ChatForwarder());
	}

	@Override
	public void onDisable() {
		chatForwarder.stop();

	}

	private void registerEventListener(Listener listener) {
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(listener, this);
	}

	private void checkFile() {
		File file = new File(getDataFolder(), "config.yml");
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			config.set("ServerAddress", "127.0.0.1");
			config.set("Port", 25565);
			try {
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
