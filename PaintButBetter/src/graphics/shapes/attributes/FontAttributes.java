package graphics.shapes.attributes;

import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class FontAttributes extends Attributes {
	
	private int size = 30;
	private Font font = new Font("Serif", Font.PLAIN, this.size);
	private Color fontColor;

	public String getId() {
		return("font");
	}
	
	public void setFont(int size) {
		this.font = new Font("Serif", Font.PLAIN, size);
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getFontSize() {
		return this.size;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public Color getFontColor() {
		return this.fontColor;
	}
	
	public Rectangle getBounds(String text) {
		// BufferedImage ??
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		return font.getStringBounds(text, frc).getBounds();
	}
}
