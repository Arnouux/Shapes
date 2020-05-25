package graphics.shapes.ui;

import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.Iterator;
import java.awt.Color;

public class ShapeDraftman implements ShapeVisitor {
	
	public ColorAttributes DEFAULTCOLORATTRIBUTES;
	
	private int HANDLER_SIZE = 2;
	
	private Graphics2D g2d;
	public ShapeDraftman(Graphics g) {
		this.g2d = (Graphics2D) g;
	}
	
	public void visitRectangle(SRectangle rect) {
		
		ColorAttributes attrColor = (ColorAttributes) rect.getAttributes("color");
		
		Rectangle rectPos = rect.getRect();
		if (attrColor.isFilled()) {
			g2d.setColor(attrColor.getColorFilled());
			g2d.fillRect(rectPos.x, rectPos.y, rectPos.width, rectPos.height);
		}
		if (attrColor.isStroked()) {
			g2d.setColor(attrColor.getColorStroked());
			g2d.drawRect(rectPos.x, rectPos.y, rectPos.width, rectPos.height);
		}
		
		SelectionAttributes attrSelec = (SelectionAttributes) rect.getAttributes("selection");

		if (attrSelec.isSelected()) {
			drawHandler(rectPos.getBounds());
		}
		
	}
	
	public void visitCircle(SCircle circle) {
		
		ColorAttributes attrColor = (ColorAttributes) circle.getAttributes("color");
		Point p = circle.getLoc();
		int r = circle.getRadius();
		if (attrColor.isFilled()) {
			g2d.setColor(attrColor.getColorFilled());
			g2d.fillOval(p.x, p.y, r, r);
		}
		if (attrColor.isStroked()) {
			g2d.setColor(attrColor.getColorStroked());
			g2d.drawOval(p.x, p.y, r, r);
		}
		
		SelectionAttributes attrSelec = (SelectionAttributes) circle.getAttributes("selection");
		if (attrSelec.isSelected()) {
			drawHandler(circle.getBounds());
		}
	}

	public void visitText(SText text) {

		ColorAttributes attrColor = (ColorAttributes) text.getAttributes("color");
		
		FontAttributes attrFont = (FontAttributes) text.getAttributes("font");
		g2d.setFont(attrFont.getFont());
		g2d.setColor(attrFont.getFontColor());
		
		Point p = text.getLoc();
		
		Rectangle r = text.getBounds();
		if (attrColor.isFilled()) {
			g2d.setColor(attrColor.getColorFilled());
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}
		if (attrColor.isStroked()) {
			g2d.setColor(attrColor.getColorStroked());
			g2d.drawRect(r.x, r.y, r.width, r.height);
		}
		g2d.drawString(text.getText(), p.x, p.y+r.height);
		
		SelectionAttributes attrSelec = (SelectionAttributes) text.getAttributes("selection");
		if (attrSelec.isSelected()) {
			drawHandler(text.getBounds());
		}
	}
	
	public void visitCollection(SCollection coll) {
		Iterator<Shape> iter = coll.iterator();
		while(iter.hasNext()) {
			iter.next().accept(this);
		}
		SelectionAttributes attrSelec = (SelectionAttributes) coll.getAttributes("selection");
		if (attrSelec.isSelected()) {
			drawHandler(coll.getBounds());
		}
	}
	
	public void drawHandler(Rectangle bounds) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(bounds.x-HANDLER_SIZE, bounds.y-HANDLER_SIZE,HANDLER_SIZE*2,HANDLER_SIZE*2);
		g2d.drawRect(bounds.x-HANDLER_SIZE+bounds.width, bounds.y-HANDLER_SIZE+bounds.height, HANDLER_SIZE*2, HANDLER_SIZE*2);
	}
	
	
}
