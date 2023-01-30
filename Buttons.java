package com.lumity.chatmonitor;

public class Buttons {
	private boolean chatToggled;
	private boolean commandsToggled;
	private boolean bothToggled;

	public Buttons() {
		bothToggled = true; // default to both
	}

	public void toggleChat() {
		chatToggled = !chatToggled;
		commandsToggled = false;
		bothToggled = false;
	}

	public void toggleCommands() {
		chatToggled = false;
		commandsToggled = !commandsToggled;
		bothToggled = false;
	}

	public void toggleBoth() {
		chatToggled = false;
		commandsToggled = false;
		bothToggled = !bothToggled;
	}

	public boolean isChatToggled() {
		return chatToggled;
	}

	public boolean isCommandsToggled() {
		return commandsToggled;
	}

	public boolean isBothToggled() {
		return bothToggled;
	}
}