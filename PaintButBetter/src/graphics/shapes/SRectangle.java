package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SRectangle extends Shape {

	private Rectangle rect = new Rectangle();
	
	public SRectangle(Point p, int height, int width) {
		this.rect.x = p.x;
		this.rect.y = p.y;
		this.rect.height = height;
		this.rect.width = width;
	}
	
	public void changeSize(int x, int y, boolean top) {
		if (top && this.rect.height-y > 1 && this.rect.width-x > 1) {
			this.rect.x += x;
			this.rect.y += y;
			this.rect.height -= y;
			this.rect.width -= x;
		}
		else if (!top && this.rect.height+y > 1 && this.rect.width+x > 1) {
			this.rect.height += y;
			this.rect.width += x;
		}
	}
	
	public Rectangle getRect() {
		return this.rect;
	}
	
	public Point getLoc() {
		Point p = new Point(this.rect.x, this.rect.y);
		return p;
	}
	
	public void setLoc(Point p) {
		this.rect.x = p.x;
		this.rect.y = p.y;
	}
	
	public void translate(int x, int y) {
		this.rect.x += x;
		this.rect.y += y;
	}
	
	public Rectangle getBounds() {
		Rectangle r = new Rectangle(this.rect);
		return r;
	}
	
	public void accept(ShapeVisitor sv) {
		sv.visitRectangle(this);
	}
}
