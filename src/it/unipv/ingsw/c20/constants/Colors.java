package it.unipv.ingsw.c20.constants;

import java.awt.Color;

public enum Colors {
	ORANGE(Color.ORANGE, "Orange"),
	BLUE(Color.BLUE, "Blue"),
	RED(Color.RED, "Red"),
	GREEN(Color.GREEN, "Green"),
	YELLOW(Color.YELLOW, "Yellow");
	
	private final Color awtColor;
	private final String colorName;
	
	private Colors(Color awtColor, String name) {
		this.awtColor = awtColor;
		this.colorName = name;
	}
	
	public Color getAwtColor() {
		return awtColor;
	}
		
	public String getColorName() {
		return colorName;
	}
}
