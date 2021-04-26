package it.unipv.ingsw.c20.constants;

import java.awt.Color;

/**
 * Enum that indicate the colors of the players
 * @author Filippo Tagliaferri
 *
 */

public enum Colors {
	ORANGE(Color.ORANGE, "Orange"),
	BLUE(Color.BLUE, "Blue"),
	RED(Color.RED, "Red"),
	GREEN(Color.GREEN, "Green"),
	YELLOW(Color.YELLOW, "Yellow");
	
	private final Color awtColor;
	private final String colorName;

	/**
	 * Creator of the enum's param 
	 * @param awtColor Color.color
 	 * @param name string of the color
	 */
	
	private Colors(Color awtColor, String name) {
		this.awtColor = awtColor;
		this.colorName = name;
	}
	
	/**
	 * awtcolor getter
	 * @return Color.color
	 */
	
	public Color getAwtColor() {
		return awtColor;
	}
		
	/**
	 * ColorName getter
	 * @return String color
	 */
	public String getColorName() {
		return colorName;
	}
}
