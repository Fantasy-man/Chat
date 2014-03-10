package de.marcel.chat.gui;

public class Location {
	
	private Integer x;
	private Integer y;
	
	public Location(Integer x, Integer y) {
		this.setX(x);
		this.setY(y);
	}
	
	
	// GETTER UND SETTER
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
	
	
	
}
