package graphics.shapes.attributes;

import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class FontAttributes extends Attributes {
	
	private int size = 30;
	private String DEFAULT_FONT = "Serif";
	
	private Font font = new Font(DEFAULT_FONT, 0, this.size);
	
	private Color fontColor;
	
	public FontAttributes(String font, int style, int size) {
		this.font = new Font(font, style, size);
	}

	public String getId() {
		return("font");
	}
	
	public void setFont(int size, String font, int type) {
		//1	bold
		//0	plain
		//2	italic
		this.font = new Font(font, type, size);
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
