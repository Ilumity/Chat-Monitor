package com.lumity.chatmonitor;

public class MessageReceiver {
	private Buttons buttons;

	public MessageReceiver(Buttons buttons) {
		this.buttons = buttons;
	}

	public void displayMessage(String message) {
		if (buttons.isChatToggled() && message.contains("(Chat)")) {
			// display message in chat area
		} else if (buttons.isCommandsToggled() && message.contains("(Command)")) {
			// display message in chat area
		} else if (buttons.isBothToggled()) {
			// display message in chat area
		}
	}
}