package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import graphics.shapes.attributes.FontAttributes;

public class SText extends Shape{
	
	private String text;
	private Point loc;
	
	public SText(Point p, String text) {
		this.loc = p;
		this.text = text;
	}
	
	public void changeSize(int x, int y, boolean top) {
		FontAttributes font = (FontAttributes) this.getAttributes("font");
		int size = font.getFontSize();
		if (top && size-x-y > 1) {
			size -= x+y;
			font.setFont(size);
			font.setSize(size);
			this.loc.x += x+y;
			this.loc.y += x+y;
		}
		else if (!top && size+x+y > 1) {
			size += x+y;
			font.setFont(size);
			font.setSize(size);

		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String str) {
		this.text = str;
	}
	
	public Point getLoc() {
		return this.loc;
	}
	
	public void setLoc(Point p) {
		this.loc.x = p.x;
		this.loc.y = p.y;
	}
	
	public void translate(int x, int y) {
		this.loc.x += x;
		this.loc.y += y;
	}
	
	public Rectangle getBounds() {
		FontAttributes attr = (FontAttributes) this.getAttributes("font");
		Rectangle r = attr.getBounds(this.text);
		Point p = getLoc();
		r.x = p.x ; r.y = p.y;
		return r;
	}
	
	public void accept(ShapeVisitor sv) {
		sv.visitText(this);
	}
}
