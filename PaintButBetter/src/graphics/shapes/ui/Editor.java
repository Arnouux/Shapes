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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

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
	static Dimension preferredSize=new Dimension(800, 800);
	
	public Editor()
	{	
		super("PAINT BUT BETTER");
		
		
		
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				System.exit(0);
			}
			
			
		});
		
		this.buildModel();
		
		this.sview = new ShapesView(this.model);
		this.sview.setPreferredSize(preferredSize);
		this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
		
		ImageIcon image = new ImageIcon("..\\img\\APP1.png");
	    this.setIconImage(image.getImage());
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
		LoadingScreen();
		Editor frame = new Editor();
		frame.pack();
		frame.setVisible(true);
	}


/************************************ SECTION BARRE DE MENU ************************************/
	
	
//	public static void createColorChooser(Editor frame)
//	{
//		ColorChooser cc = new ColorChooser();
//		Dialog color= new Dialog(frame, "Color Panel");
//		color.setPreferredSize(new Dimension(600, 600));
//		color.setLocationRelativeTo(frame);
//		color.pack();
//		color.add(cc);
//		color.setVisible(true);
//		color.pack();
//	}
	
	public static void createModificationPanel(Editor frame)
	{
		Dialog mp = new Dialog(frame);
		JPanel jp;
	}
	
	
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
	    this.setJMenuBar(menuBar);
	    JMenu createNewShape = new JMenu("New Shape");
	    JMenuItem circle = new JMenuItem("Circle");
	    JMenuItem rectangle = new JMenuItem("Rectangle");
	    JMenuItem text = new JMenuItem("Text");

	    menuBar.add(createNewShape);
	    createNewShape.add(rectangle);
	    createNewShape.add(circle);
	    createNewShape.add(text);

	    rectangle.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	createDefaultRectangle(); 
	    	}});
	    
	    circle.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	createDefaultCircle(); 
	    	}});
	    
	    text.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	createDefaultText(); 
	    	}});
    
	}

	public void createDefaultCircle() {
		Color filledColor = Color.BLACK;
		Color strokedColor = Color.BLACK;
		Color chosenColor = null;

		chosenColor = JColorChooser.showDialog(this, "Circle fill color chooser", filledColor);
		if (chosenColor != null) filledColor = chosenColor;
		
		
		Rectangle windowBounds= this.getBounds();
		SCircle c = new SCircle(new Point(windowBounds.width/2,windowBounds.height/2), Math.min(windowBounds.width, windowBounds.height)/5);
		c.addAttributes(new ColorAttributes(true,true,filledColor,strokedColor));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);
		this.sview.invalidate();
		
	}	
	
	public void createDefaultRectangle() {
		
		Color filledColor = Color.BLACK;
		Color strokedColor = Color.BLACK;
		Color chosenColor = null;

		chosenColor = JColorChooser.showDialog(this, "Rectangle  fill color chooser", filledColor);
		if (chosenColor != null) filledColor = chosenColor;

		chosenColor = JColorChooser.showDialog(this, "Rectangle strock color chooser", strokedColor);
		
		
		Rectangle windowBounds= this.getBounds();
		SRectangle r = new SRectangle(new Point(windowBounds.width/2,windowBounds.height/2),Math.min(windowBounds.width, windowBounds.height)/5,Math.min(windowBounds.width, windowBounds.height)/5);
		r.addAttributes(new ColorAttributes(true,true,filledColor,strokedColor));
		r.addAttributes(new SelectionAttributes());
		this.model.add(r);
		this.sview.invalidate();

	}
	
	public void createDefaultText(){
		Color filledColor = Color.BLACK;
		Color strokedColor = Color.BLACK;
		Color chosenColor = null;

		chosenColor = JColorChooser.showDialog(this, "Circle fill color chooser", filledColor);
		if (chosenColor != null) filledColor = chosenColor;

		chosenColor = JColorChooser.showDialog(this, "Circle strock color chooser", strokedColor);
		String response = JOptionPane.showInputDialog("What's your text ?");
		Rectangle windowBounds=this.getBounds();
		SText t= new SText(new Point(windowBounds.width/2,windowBounds.height/2),response);
		t.addAttributes(new ColorAttributes(true,true,filledColor,strokedColor));
		t.addAttributes(new SelectionAttributes());
	    t.addAttributes(new FontAttributes());
	    this.model.add(t);
	    this.sview.invalidate();
	}
	
	public static void LoadingScreen()
	{	
		JFrame ls = new JFrame();
		ImageIcon waitingScreen = new ImageIcon("..\\img\\APP1.png");
		JLabel ws=new JLabel(waitingScreen);
		ws.setSize(preferredSize.height*(4/5), preferredSize.height*(4/5) );
		ls.add(ws);
		ls.setPreferredSize(preferredSize);
	    ls.setIconImage(waitingScreen.getImage());
	    ls.pack();
	    ls.setVisible(true);
	    long t= System.currentTimeMillis();
	    long end = t+1500;
	    while(System.currentTimeMillis() < end) {}
		ls.setVisible(false);
		ls.dispatchEvent(new WindowEvent(ls, WindowEvent.WINDOW_CLOSING));
		
	}
	
	
}

	

