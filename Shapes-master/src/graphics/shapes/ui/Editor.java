package graphics.shapes.ui;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.ColorChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Editor extends JFrame
{
	ShapesView sview;
	SCollection model=new SCollection();
	ColorChooser cc = new ColorChooser();

	
	public Editor()
	{	
		super("Shapes Editor");

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				System.exit(0);
			}
		});

		this.buildModel();
		
		this.sview = new ShapesView(this.model);
		this.sview.setPreferredSize(new Dimension(800,800));
		this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
		
		ImageIcon image = new ImageIcon("..\\td_shapes\\img\\Icone.png");
	    this.setIconImage(image.getImage());
	    //new
	    createMenuBar();
	    
	    
	    
	}	
		
	
	private void buildModel()
	{
		this.model = new SCollection();
		this.model.addAttributes(new SelectionAttributes());
		SRectangle r = new SRectangle(new Point(50,170),40,30);
		r.addAttributes(new ColorAttributes(true,false,Color.YELLOW,Color.RED));
		r.addAttributes(new SelectionAttributes());
		this.model.add(r);

		SRectangle r2 = new SRectangle(new Point(150,100),190,140);
		r2.addAttributes(new ColorAttributes(true,true,Color.GREEN,Color.GREEN));
		r2.addAttributes(new SelectionAttributes());
		this.model.add(r2);
	
		SCircle c = new SCircle(new Point(400,400),20);
		c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.BLUE));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);
		

		
		SText t= new SText(new Point(100,200),"hello");
		t.addAttributes(new ColorAttributes(true,true,Color.YELLOW,Color.BLUE));
		t.addAttributes(new FontAttributes());
		t.addAttributes(new SelectionAttributes());
		this.model.add(t);
		
		SCollection sc = new SCollection();
		sc.addAttributes(new SelectionAttributes());
		r= new SRectangle(new Point(20,30),30,30);
		r.addAttributes(new ColorAttributes(true,false,Color.MAGENTA,Color.BLUE));
		r.addAttributes(new SelectionAttributes());
		sc.add(r);
		c = new SCircle(new Point(150,100),40);
		c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.DARK_GRAY));
		c.addAttributes(new SelectionAttributes());
		sc.add(c);
		this.model.add(sc);
	}
	
	public static void main(String[] args)
	{
		Editor frame = new Editor();
		
		//createColorChooser(frame); //new
		
		frame.pack();
		frame.setVisible(true);
	}


/************************************ SECTION BARRE DE MENU ************************************/
	
	
	public static void createColorChooser(Editor frame)
	{
		ColorChooser cc = new ColorChooser();
		Dialog color= new Dialog(frame, "Color Panel");
		color.setPreferredSize(new Dimension(600, 600));
		color.setLocationRelativeTo(frame);
		color.pack();
		color.add(cc);
		color.setVisible(true);
		color.pack();
	}
	
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
	    this.setJMenuBar(menuBar);
	    
	    JMenu menu = new JMenu("File");
	    JMenu circle = new JMenu("Circle");
	    JMenu rectangle = new JMenu("Rectangle");

	    menuBar.add(menu);
	    menuBar.add(circle);
	    menuBar.add(rectangle);
	    
	   

	    
	    rectangle.addMenuListener(new MenuListener() {

	        @Override
	        public void menuSelected(MenuEvent e) {
	            createDefaultRectangle();
	            
		    		
	        }
	        @Override
	        public void menuDeselected(MenuEvent e) {
	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	        }
	    });
	    
	    circle.addMenuListener(new MenuListener() {

	        @Override
	        public void menuSelected(MenuEvent e) {
	        	createDefaultCircle();
	            	
	        }
	        @Override
	        public void menuDeselected(MenuEvent e) {
	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	        }
	    });
	    
	   
	    
	}

	public void createDefaultCircle() {
		Color Filled = JColorChooser.showDialog(this.cc,"Choose Circle Fill Color",cc.banner.getBackground());
		Color Stroked = JColorChooser.showDialog(this.cc,"Choose Circle Strock Color",cc.banner.getBackground());
		Rectangle windowBounds= this.getBounds();
		SCircle c = new SCircle(new Point(windowBounds.width/2,windowBounds.height/2), Math.min(windowBounds.width, windowBounds.height)/5);
		c.addAttributes(new ColorAttributes(true,true,Filled,Stroked));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);
		this.sview.invalidate();
	}	
	
	public void createDefaultRectangle() {
		Color Filled = JColorChooser.showDialog(this.cc,"Choose Circle Color",cc.banner.getBackground());
		Color Stroked = JColorChooser.showDialog(this.cc,"Choose Circle Color",cc.banner.getBackground());
		Rectangle windowBounds= this.getBounds();
		SRectangle r = new SRectangle(new Point(windowBounds.width/2,windowBounds.height/2),Math.min(windowBounds.width, windowBounds.height)/5,Math.min(windowBounds.width, windowBounds.height)/5);
		r.addAttributes(new ColorAttributes(true,true,Filled,Stroked));
		r.addAttributes(new SelectionAttributes());
		this.model.add(r);
		this.sview.invalidate();
	}
	
	public void createDefaultText(){
		Color Filled = JColorChooser.showDialog(this.cc,"Choose Circle Color",cc.banner.getBackground());
		Color Stroked = JColorChooser.showDialog(this.cc,"Choose Circle Color",cc.banner.getBackground());
		String response = JOptionPane.showInputDialog("choose a text !");
		Rectangle windowBounds=this.getBounds();
		SText t= new SText(new Point(windowBounds.width/2,windowBounds.height/2),response);
		t.addAttributes(new ColorAttributes(true,true,Filled,Stroked));
		t.addAttributes(new SelectionAttributes());
	    t.addAttributes(new FontAttributes());
	    this.model.add(t);
	    this.sview.invalidate();
	}
	}



