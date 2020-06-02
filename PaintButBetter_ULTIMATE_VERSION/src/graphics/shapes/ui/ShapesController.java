package graphics.shapes.ui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Rectangle;

import graphics.ui.Controller;
import graphics.ui.View;
import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.Editor;

public class ShapesController extends Controller {
	
	private SCollection model;
	private ShapesView view;
	
	private ArrayList<SCollection> historic = new ArrayList<SCollection>();
	
	private int HANDLER_SIZE_ADMITTER = 6;
	
	private boolean translatable = true;
	private boolean handlingTop = false;
	private boolean handlingBot = false;
	private Shape shapeHandling = null;
	private Point mouseCoordonates = new Point(0,0);
	private Shape Ctrl_C = null;
	
	public ShapesController(Object model) {
		super(model);
		this.model = (SCollection) model;
		this.historic.add((SCollection) model);
	}

	public void setView(View view)
	{
		this.view = (ShapesView) view;
	}
	
	public ArrayList<SCollection> getHistoric() {
		return this.historic;
	}
	
	public Shape getTarget(Point p) {
		Iterator<Shape> iter = this.model.iterator();
		Shape shape_top = null;
		while(iter.hasNext()) {
			Shape shape = iter.next();
			Rectangle bounds = shape.getBounds();
			if (p.x >= bounds.x && p.x <= bounds.x+bounds.width &&
				p.y >= bounds.y && p.y <= bounds.y+bounds.height) {
				shape_top = shape;
			}
		}
		return shape_top;
	}
	
	public Shape getTargetFromHandler(Point p) {

		return getTarget(p);
	}
	
	public void unselectAll() {
		Iterator<Shape> iter = this.model.iterator();
		while(iter.hasNext()) {
			SelectionAttributes attr = (SelectionAttributes)iter.next().getAttributes("selection");
			attr.unselect();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.mouseCoordonates.setLocation(e.getPoint());
		
		Shape shape = getTarget(e.getPoint());
		System.out.println(shape);
		
		if (!e.isShiftDown()) {
			unselectAll();
		}	
		
		if (shape != null) {
			SelectionAttributes selec = (SelectionAttributes)shape.getAttributes("selection");

			selec.toggleSelection();
		}
		this.translatable = false;
		this.view.invalidate();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseCoordonates.setLocation(e.getPoint());
		
		Shape shape = getTarget(e.getPoint());
		if (shape != null) {
			SelectionAttributes selec = (SelectionAttributes)shape.getAttributes("selection");
			if (selec.isSelected()) {
				this.translatable = true;
			}
			else {this.translatable = false; }
		}
		else {this.translatable = false; }
		
		this.view.invalidate();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		this.handlingTop = false;
		this.handlingBot = false;
		
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		
		this.view.setCursor(cursor);
		
		this.historic.add(this.model.copy());
	}
	
	@Override
	public void mouseMoved(MouseEvent evt)
	{
		int cursor_int = Cursor.DEFAULT_CURSOR;
				
		Iterator<Shape> iter = this.model.iterator();
		while(iter.hasNext()) {
			Shape shape = iter.next();
			SelectionAttributes selec = (SelectionAttributes)shape.getAttributes("selection");
			if (selec.isSelected()) {
				Rectangle bounds = shape.getBounds();
				if (bounds.x-HANDLER_SIZE_ADMITTER < evt.getX() && bounds.x+HANDLER_SIZE_ADMITTER > evt.getX() &&
						bounds.y-HANDLER_SIZE_ADMITTER < evt.getY() && bounds.y+HANDLER_SIZE_ADMITTER > evt.getY() ||
						bounds.x+bounds.width-HANDLER_SIZE_ADMITTER < evt.getX() && bounds.x+bounds.width+HANDLER_SIZE_ADMITTER > evt.getX() &&
						 bounds.y+bounds.height-HANDLER_SIZE_ADMITTER < evt.getY() && bounds.y+bounds.height+HANDLER_SIZE_ADMITTER > evt.getY()) {
					
				cursor_int = Cursor.NW_RESIZE_CURSOR;	
					
				}
			}
		}
		
		Cursor cursor = new Cursor(cursor_int);
		this.view.setCursor(cursor);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		
		if (this.handlingTop) {
			this.shapeHandling.changeSize(e.getX()-this.mouseCoordonates.x,
							 e.getY()-this.mouseCoordonates.y,
							 true, e.isShiftDown());
		}
		
		else if (this.handlingBot) {
			this.shapeHandling.changeSize(e.getX()-this.mouseCoordonates.x,
					 		 e.getY()-this.mouseCoordonates.y,
					 		 false, e.isShiftDown());
		}

		if (!this.handlingBot && !this.handlingTop) {
			Shape shape_target = getTargetFromHandler(e.getPoint());
			
			if (shape_target != null) {
				Rectangle bounds = shape_target.getBounds();
				SelectionAttributes selec = (SelectionAttributes)shape_target.getAttributes("selection");
				if (selec.isSelected()) {
					if (bounds.x-HANDLER_SIZE_ADMITTER < e.getX() && bounds.x+HANDLER_SIZE_ADMITTER > e.getX() &&
						bounds.y-HANDLER_SIZE_ADMITTER < e.getY() && bounds.y+HANDLER_SIZE_ADMITTER > e.getY()) {
						System.out.println("Handler top");
						this.handlingTop = true;
						shapeHandling = shape_target;
			
						shape_target.changeSize(e.getX()-this.mouseCoordonates.x,
											 e.getY()-this.mouseCoordonates.y,
											 true, e.isShiftDown());
						Cursor cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
						this.view.setCursor(cursor);
						this.translatable = false;
					}
					else if (bounds.x+bounds.width-HANDLER_SIZE_ADMITTER < e.getX() && bounds.x+bounds.width+HANDLER_SIZE_ADMITTER > e.getX() &&
							 bounds.y+bounds.height-HANDLER_SIZE_ADMITTER < e.getY() && bounds.y+bounds.height+HANDLER_SIZE_ADMITTER > e.getY()) {
						System.out.println("Handler bot");
						this.handlingBot = true;
						shapeHandling = shape_target;
			
						shape_target.changeSize(e.getX()-this.mouseCoordonates.x,
											 e.getY()-this.mouseCoordonates.y,
											 false, e.isShiftDown());
						Cursor cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
						this.view.setCursor(cursor);
						this.translatable = false;
					}
				}
			}
			
			if (this.translatable) {
				Iterator<Shape> iter = this.model.iterator();
				while(iter.hasNext()) {
					Shape shape = iter.next();
					SelectionAttributes selec = (SelectionAttributes)shape.getAttributes("selection");
					if (selec.isSelected()) {

						shape.translate(e.getX()-this.mouseCoordonates.x,
											e.getY()-this.mouseCoordonates.y);
						
						Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
						
						this.view.setCursor(cursor);
					}
				}
				
			}
		}
		

		this.mouseCoordonates.setLocation(e.getPoint());
		this.view.invalidate();
	}
	
	
	
	/**
	 * Gets the value of the key pressed, then decides an action to make accordingly.
	 * 
	 * Keys :
	 * A : puts the selected Shape(s) on first plan.
	 * E : puts the selected Shape(s) on last plan.
	 * D : deletes the Shape under the cursor's coordinates.
	 * C : forms or divides a collection.
	 * Z : prints all Shapes.
	 */
	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		// get x,y relative from window position.
		Point p_window = view.getLocationOnScreen();
		p.x -= p_window.x; p.y -= p_window.y ;
		
		if (key == KeyEvent.VK_A) {
			Iterator<Shape> iter = this.model.iterator();
			
			SCollection new_model = new SCollection();
			while(iter.hasNext()){
				Shape s = iter.next();
				SelectionAttributes selec = (SelectionAttributes) s.getAttributes("selection");
				
				if (selec.isSelected()) {
					iter.remove();
					new_model.add(s);
				}
			}
			Iterator<Shape> iter_new = new_model.iterator();
			while(iter_new.hasNext()) {
				this.model.add(iter_new.next());
			}
		}
		
		else if (key == KeyEvent.VK_E) {	
			Iterator<Shape> iter = this.model.iterator();
			
			SCollection new_model = new SCollection();
			new_model.addAttributes(new SelectionAttributes());
			
			while(iter.hasNext()){
				Shape s = iter.next();
				SelectionAttributes selec = (SelectionAttributes) s.getAttributes("selection");
				
				if (selec.isSelected()) {
					iter.remove();
					new_model.add(s);
				}
			}
			
			iter = this.model.iterator();
			while(iter.hasNext()) {
				new_model.add(iter.next());
			}
			

			this.model = new_model;
			this.view.setModel(this.model);			
		}
		
		else if (key == KeyEvent.VK_D) {
			Shape shape = getTarget(p);
			Iterator<Shape> iter = this.model.iterator();
			while(iter.hasNext()){
				Shape s = iter.next();
				if(s == shape){
					iter.remove();
					break;
			   }
			}
		}
		
		else if (key == KeyEvent.VK_Z) {
			
			if (evt.isControlDown()) {
				System.out.println(this.historic.size());
				if(this.historic.size()>0)
				{
					this.model = this.historic.get(this.historic.size() - 1);
				
					for (int i = 0; i < this.historic.size(); i++) {
						Iterator<Shape> iter = this.historic.get(i).iterator();
						System.out.println("-------------------");
						while(iter.hasNext()){
							System.out.println(iter.next());
						}
					}
					this.view.setModel(this.model);
					this.historic.remove(this.historic.size() - 1);
				}
				
			}
			else {
				Iterator<Shape> iter = this.model.iterator();
				System.out.println("-------------------");
				while(iter.hasNext()){
					System.out.println(iter.next());
				}
			}
		}
		
		else if (key == KeyEvent.VK_C) {
			if(evt.isControlDown()) {
				Ctrl_C=getTarget(p);
			}
			
			else {
				Iterator<Shape> iter = this.model.iterator();
				
				SCollection new_model = new SCollection();
				new_model.addAttributes(new SelectionAttributes());
	
				while(iter.hasNext()) {
					Shape shape = iter.next();
					SelectionAttributes selec = (SelectionAttributes)shape.getAttributes("selection");
					if (selec.isSelected()) {
						iter.remove();
						selec.unselect();
						new_model.add(shape);
					}
				}
			
				Iterator<Shape> new_iter = new_model.iterator();
				if (new_model.getArraySize() == 1) {
					Shape coll = new_iter.next();
					if (coll instanceof SCollection) {
						new_iter = ((SCollection) coll).iterator();
						while(new_iter.hasNext()) {
							this.model.add(new_iter.next());
						}
					}
					else {
						this.model.add(coll);
					}
				}
				else if (new_model.getArraySize() >= 2) {
					this.model.add(new_model);
			}
				
			File clip = new File("sound\\Collection.wav");
			Editor.playSound(clip);
		}
		}
		
		else if (key == KeyEvent.VK_V && evt.isControlDown()) {
			this.model.add(clone(Ctrl_C));
				
		}
	
		
		this.view.invalidate();
	}
		
	public Shape clone(Shape shape) {
		Shape newShape =null;
		
		if(shape instanceof SRectangle) {
			SRectangle rec =(SRectangle) shape;
			newShape = new SRectangle(new Point(rec.getLoc().x,rec.getLoc().y), rec.getRect().height, rec.getRect().width);
			ColorAttributes ca = (ColorAttributes) rec.getAttributes("color");
			newShape.addAttributes(new ColorAttributes(ca.isFilled(),ca.isStroked(),ca.getColorFilled(),ca.getColorStroked()));
		}
		
		else if( shape instanceof SCircle) {
			SCircle cir =(SCircle) shape;
			newShape = new SCircle(new Point(cir.getLoc().x,cir.getLoc().y),cir.getRadius());
			ColorAttributes ca = (ColorAttributes) cir.getAttributes("color");
			newShape.addAttributes(new ColorAttributes(ca.isFilled(),ca.isStroked(),ca.getColorFilled(),ca.getColorStroked()));
		}
		
		else if(shape instanceof SText) {
			SText text =(SText) shape;
			newShape = new SText(new Point(text.getLoc().x,text.getLoc().y),text.getText());
			ColorAttributes ca = (ColorAttributes) text.getAttributes("color");
			FontAttributes fa = (FontAttributes)text.getAttributes("font");
			newShape.addAttributes(new ColorAttributes(ca.isFilled(),ca.isStroked(),ca.getColorFilled(),ca.getColorStroked()));
			newShape.addAttributes(new FontAttributes(fa.getFont().getName(), fa.getFont().getStyle(), fa.getFont().getSize()));
		}
		
		else if(shape instanceof SCollection) {
			SCollection col =(SCollection) shape;
			newShape = new SCollection();
			for(Shape shap : col.getCollection()) {
				((SCollection) newShape).add(clone(shap));
			}
		}	
		newShape.addAttributes(new SelectionAttributes());
		return newShape;

		}


}