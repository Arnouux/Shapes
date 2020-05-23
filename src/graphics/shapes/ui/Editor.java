package graphics.shapes.ui;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Editor extends JFrame
{
	ShapesView sview;
	SCollection model=new SCollection();


	
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
		this.sview.setPreferredSize(new Dimension(500,500));
		this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
		
		ImageIcon image = new ImageIcon("..\\td_shapes\\img\\Icone.png");
	    this.setIconImage(image.getImage());
	    
		
		JMenuBar menuBar = new JMenuBar();
	    this.setJMenuBar(menuBar);
	    
	    JMenu menu = new JMenu("File");
	    
	    JMenu circle = new JMenu("Circle");
	    
	    
	    JMenu rectangle = new JMenu("Rectangle");
	    
	    
	    
	    JMenuItem rPink = new JMenuItem("Pink");
	    JMenuItem rRed = new JMenuItem("Red");
	    JMenuItem rYellow = new JMenuItem("Yellow");
	    JMenuItem rBlue = new JMenuItem("Blue");
	    JMenuItem rGreen = new JMenuItem("Green");
	    
	    JMenuItem cPink = new JMenuItem("Pink");
	    JMenuItem cRed = new JMenuItem("Red");
	    JMenuItem cYellow = new JMenuItem("Yellow");
	    JMenuItem cBlue = new JMenuItem("Blue");
	    JMenuItem cGreen = new JMenuItem("Green");
	    
	    //Barre de Menu Principale
	    menuBar.add(menu);
	    menuBar.add(circle);
	    menuBar.add(rectangle);
	    
	    //Barre de MenuItem de Rectangle
	    rectangle.add(rPink);
	    rectangle.add(rRed);
	    rectangle.add(rYellow);
	    rectangle.add(rBlue);
	    rectangle.add(rGreen);
	    
	  //Barre de MenuItem de Circle
	    circle.add(cPink);
	    circle.add(cRed);
	    circle.add(cYellow);
	    circle.add(cBlue);
	    circle.add(cGreen);
	    

	    
	    rectangle.addMenuListener(new MenuListener() {

	        @Override
	        public void menuSelected(MenuEvent e) {
	            System.out.println("Color ?");
	            
		    		
	        }
	        @Override
	        public void menuDeselected(MenuEvent e) {
	            System.out.println("menuDeselected");

	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	            System.out.println("menuCanceled");

	        }
	    });
	    
	    circle.addMenuListener(new MenuListener() {

	        @Override
	        public void menuSelected(MenuEvent e) {
	            System.out.println("Color ?");
	            
		    		
	        }
	        @Override
	        public void menuDeselected(MenuEvent e) {
	            System.out.println("menuDeselected");

	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	            System.out.println("menuCanceled");

	        }
	    });
	    
	    cPink.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		createDefaultCircle(Color.pink);
		    		
		    	}
		    });
	    cRed.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		createDefaultCircle(Color.red);
		    		
		    	}
		    });
	    cYellow.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		createDefaultCircle(Color.yellow);
		    		
		    	}
		    });
	    cBlue.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		createDefaultCircle(Color.blue);
		    		
		    	}
		    });
	    cGreen.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		createDefaultCircle(Color.green);
		    		
		    	}
		    });
	    
	    
	    rPink.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		System.out.print("Your new rectangle has been created");
		    		createDefaultRectangle(Color.pink);
		    		
		    	}
		    });
	    rRed.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		System.out.print("Your new rectangle has been created");
		    		createDefaultRectangle(Color.red);
		    		
		    	}
		    });
	    rYellow.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		System.out.print("Your new rectangle has been created");
		    		createDefaultRectangle(Color.yellow);
		    		
		    	}
		    });
	    rBlue.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		System.out.print("Your new rectangle has been created");
		    		createDefaultRectangle(Color.blue);
		    		
		    	}
		    });
	    rGreen.addActionListener(
		    	new ActionListener(){
		    	public void actionPerformed(ActionEvent evt) {
		    		System.out.print("Your new rectangle has been created");
		    		createDefaultRectangle(Color.green);
		    		
		    	}
		    });
	   
	    
	}
	
	public void createDefaultCircle(Color col) {
		Rectangle windowBounds= this.getBounds();
		SCircle c = new SCircle(new Point(windowBounds.width/2,windowBounds.height/2), Math.min(windowBounds.width, windowBounds.height)/5);
		c.addAttributes(new ColorAttributes(true,true,col,Color.RED));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);
		this.invalidate();
	}
	
	public void createDefaultRectangle(Color col) {
		Rectangle windowBounds= this.getBounds();
		SRectangle r = new SRectangle(new Point(windowBounds.width/2,windowBounds.height/2),Math.min(windowBounds.width, windowBounds.height)/5,Math.min(windowBounds.width, windowBounds.height)/5);
		r.addAttributes(new ColorAttributes(true,true,col,Color.RED));
		r.addAttributes(new SelectionAttributes());
		this.model.add(r);
		this.invalidate();
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

		frame.pack();
		frame.setVisible(true);
	}
}
