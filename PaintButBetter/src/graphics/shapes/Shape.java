package graphics.shapes;

import graphics.shapes.attributes.Attributes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public abstract class Shape {

	private Map<String, Attributes> attributes = new HashMap<String, Attributes>();
	
	public void addAttributes(Attributes a) {
		attributes.put(a.getId(), a);
	}
	
	public Attributes getAttributes(String str) {
		return attributes.get(str);
	}
	
	public abstract Point getLoc();
	
	public abstract void setLoc(Point p);
	
	public abstract void translate(int x, int y);
	
	public abstract Rectangle getBounds();
	
	public abstract void accept(ShapeVisitor sv);
	
	public abstract void changeSize(int x, int y, boolean top);
}
