package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;

import graphics.shapes.attributes.SelectionAttributes;

import java.util.ArrayList;

public class SCollection extends Shape {

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public Iterator<Shape> iterator() {
		return shapes.iterator();
	}
	
	public void changeSize(int x, int y, boolean top, boolean e) {
		for (int i=0; i < shapes.size(); ++i) {
			shapes.get(i).changeSize(x, y, top, e);
		}
	}
	
	public int getArraySize() {
		return shapes.size();
	}
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public Point getLoc() {
		float meanX = 0;
		float meanY = 0;
		int size = shapes.size();
		for (int i=0; i < size; ++i) {
			meanX += shapes.get(i).getLoc().x;
			meanY += shapes.get(i).getLoc().y;
		}
		Point p = new Point((int)meanX/(size+1), (int)meanY/(size+1));
		return p;
	}
	
	public void setLoc(Point p) {
		for (int i=0; i < shapes.size(); ++i) {
			shapes.get(i).setLoc(p);
		}
	}
	
	public void translate(int x, int y) {
		for (int i=0; i < shapes.size(); ++i) {
			shapes.get(i).translate(x, y);
		}
	}
	
	public Rectangle getBounds() {
		int xMax = 0;
		int yMax = 0;
		int xMin = 10000;
		int yMin = 10000;
		Rectangle bound = new Rectangle();
		Iterator<Shape> iter = this.iterator();
		while(iter.hasNext()) {
			Shape shape = iter.next();
			bound.setBounds(shape.getBounds());


			if (bound.x < xMin) { xMin = bound.x;}
			if (bound.y < yMin) { yMin = bound.y;}
			if (bound.x+bound.width > xMax) { xMax = bound.x+bound.width;}
			if (bound.y+bound.height > yMax) { yMax = bound.y+bound.height;}
		}
		bound.setBounds(xMin, yMin, xMax-xMin, yMax-yMin);
		return bound;
		
	}
	
	public void accept(ShapeVisitor sv) {
		sv.visitCollection(this);
	}
	
	public SCollection copy() {
        SCollection new_model = new SCollection();
        new_model.addAttributes(new SelectionAttributes());
        Iterator<Shape> iter = this.iterator();
        while(iter.hasNext()) {
            new_model.shapes.add(iter.next().copy());
        }
        return new_model;
    }
	
	public ArrayList<Shape> getCollection() {
		return shapes;
	}
	
}
