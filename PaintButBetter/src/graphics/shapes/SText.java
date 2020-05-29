package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;

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
	
	public SText copy() {
        SText new_text = new SText(this.loc, this.text);
        ColorAttributes color = (ColorAttributes) this.getAttributes("color");
        new_text.addAttributes(new ColorAttributes(color.isFilled(),color.isStroked(),color.getColorFilled(),color.getColorStroked()));
        new_text.addAttributes(new FontAttributes());
        FontAttributes font = (FontAttributes) this.getAttributes("font");
        FontAttributes new_font = (FontAttributes) new_text.getAttributes("font");
        new_font.setFont(font.getFontSize());
        new_text.addAttributes(new SelectionAttributes());
        return new_text;
    }
}
