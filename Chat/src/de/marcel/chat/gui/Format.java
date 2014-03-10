package de.marcel.chat.gui;

public enum Format {
	NORMAL(18F);
	
	private Float ChatTextSize;
	
	private Format(Float textSize) {
		this.ChatTextSize = textSize;
	}

	public Float getChatTextSize() {
		return ChatTextSize;
	}
}
