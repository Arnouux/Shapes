package graphics.shapes.attributes;

import java.awt.Color;

public class ColorAttributes extends Attributes {

	private boolean filled;
	private boolean stroked;
	private Color filledColor;
	private Color strokedColor;
	
	public ColorAttributes(boolean filled, boolean stroked, Color filledColor, Color strokedColor) {
		this.filled = filled;
		this.stroked = stroked;
		this.filledColor = filledColor;
		this.strokedColor = strokedColor;
	}
	
	public Color getColorFilled() {
		return this.filledColor;
	}
	
	public Color getColorStroked() {
		return this.strokedColor;
	}
	
	public boolean isFilled() {
		return this.filled;
	}
	
	public boolean isStroked() {
		return this.stroked;
	}
	
	public String getId() {
		return("color");
	}
	
	
}
