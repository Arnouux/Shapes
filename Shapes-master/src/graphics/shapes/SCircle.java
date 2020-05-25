package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends Shape {
	
	private int radius;
	private Point loc;
	
	public SCircle(Point p, int radius) {
		this.loc = p;
		this.radius = radius;
	}
	
	public void changeSize(int x, int y, boolean top) {
		if (top && this.radius-x-y > 1) {
			this.radius -= x+y;
			this.loc.x += x+y;
			this.loc.y += x+y;
		}
		else if (!top && this.radius+x+y > 1) {
			this.radius += x+y;
		}
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void setRadius(int r) {
		this.radius = r;
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
		Rectangle r = new Rectangle(this.loc.x,
									this.loc.y,
									this.radius,
									this.radius);
		return r;
	}
	
	public void accept(ShapeVisitor sv) {
		sv.visitCircle(this);
	}
}
