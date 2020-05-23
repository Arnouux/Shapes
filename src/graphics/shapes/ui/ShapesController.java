package graphics.shapes.ui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.awt.Rectangle;

import graphics.ui.Controller;
import graphics.ui.View;
import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.shapes.attributes.SelectionAttributes;

public class ShapesController extends Controller {
	
	private SCollection model;
	private ShapesView view;
	
	private int HANDLER_SIZE_ADMITTER = 6;
	
	private boolean translatable = true;
	private boolean handlingTop = false;
	private boolean handlingBot = false;
	private Shape shapeHandling = null;
	private Point mouseCoordonates = new Point(0,0);
	
	public ShapesController(Object model) {
		super(model);
		this.model = (SCollection) model;
	}

	public void setView(View view)
	{
		this.view = (ShapesView) view;
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
							 true);
		}
		
		else if (this.handlingBot) {
			this.shapeHandling.changeSize(e.getX()-this.mouseCoordonates.x,
					 		 e.getY()-this.mouseCoordonates.y,
					 		 false);
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
											 true);
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
											 false);
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
			this.view.setModel(new_model);			
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
			Iterator<Shape> iter = this.model.iterator();
			System.out.println("-------------------");
			while(iter.hasNext()){
				System.out.println(iter.next());
			}
		}
		
		else if (key == KeyEvent.VK_C) {
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
				System.out.println("1 Shape");
				if (new_iter.next() instanceof SCollection) {
					
				}
			}
			else if (new_model.getArraySize() >= 2) {
				this.model.add(new_model);
			}
			
			System.out.println(this.model.getArraySize() + " " + new_model.getArraySize());
		}
		
		this.view.invalidate();
	}


}
