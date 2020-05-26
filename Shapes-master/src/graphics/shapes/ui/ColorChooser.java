package graphics.shapes.ui;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 



//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.event.*;
//import javax.swing.colorchooser.*;
//
///* ColorChooserDemo.java requires no other files. */
//public class ColorChooser extends JPanel
//                              implements ChangeListener {
//
//	public JColorChooser cc;
//    public JLabel banner;
//    public Color color;
//
//    public ColorChooser() {
//        super(new BorderLayout());
//
//        //Set up the banner at the top of the window
//        banner = new JLabel("Paint But Better",
//                            JLabel.CENTER);
//        banner.setForeground(Color.black);
//        banner.setBackground(Color.white);
//        banner.setOpaque(true);
//        banner.setFont(new Font("SansSerif", Font.BOLD, 24));
//        banner.setPreferredSize(new Dimension(50, 65));
//
//        JPanel bannerPanel = new JPanel(new BorderLayout());
//        bannerPanel.add(banner, BorderLayout.CENTER);
//        //bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));
//
//        //Set up color chooser for setting text color
//        cc = new JColorChooser(banner.getForeground());
//        cc.getSelectionModel().addChangeListener(this);
//        cc.setBorder(BorderFactory.createTitledBorder(
//                                             "Choose Text Color"));
//        this.color=cc.getColor();
//        add(bannerPanel, BorderLayout.CENTER);
//        add(cc, BorderLayout.PAGE_END);
//    }
//
//    public void stateChanged(ChangeEvent e) {
//    	this.color = cc.getColor();
//        banner.setForeground(this.color);
//        banner.setBackground(this.color);
//        
//        System.out.println(this.color);
//        System.out.println(cc.getColor());
//        
//    }
//    
//    public Color getColor() {
//    	System.out.println("CCCCC"+this.color);
//    	System.out.println("CCCCC"+cc.getColor());
//    	//this.color=cc.getColor();
//        //return this.color;
//    	return banner.getForeground();
//    }
//
//
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event-dispatching thread.
//     */
//    private static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame frame = new JFrame("ColorChooser");
//        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        frame.setPreferredSize(new Dimension(700, 700));
//        //Create and set up the content pane.
//        ColorChooser cc = new ColorChooser();
//        cc.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(cc);
//
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//      
//}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class ColorChooser extends JPanel implements ActionListener,ChangeListener {
	
public JLabel banner;
public JColorChooser cc1, cc2;

public ColorChooser() {
super(new BorderLayout());

//Set up banner to use as custom preview panel
banner = new JLabel("Paint but Better",JLabel.CENTER);
banner.setForeground(Color.white);
banner.setBackground(Color.blue);
banner.setOpaque(true);
banner.setFont(new Font("SansSerif", Font.BOLD, 24));
banner.setPreferredSize(new Dimension(100, 65));

JPanel bannerPanel = new JPanel(new BorderLayout());
bannerPanel.add(banner, BorderLayout.CENTER);
bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

//Set up color chooser for setting background color
cc1 = new JColorChooser();
cc1.getSelectionModel().addChangeListener(this);
cc1.setBorder(BorderFactory.createTitledBorder("Choose Font Color"));



//Remove the preview panel
cc1.setPreviewPanel(new JPanel());


//Override the chooser panels with our own


add(bannerPanel, BorderLayout.PAGE_START);
add(cc1, BorderLayout.CENTER);

}

public void actionPerformed(ActionEvent e) {
Color newColor = JColorChooser.showDialog(ColorChooser.this,"Choose Background Color",banner.getBackground());
if (newColor != null) {
banner.setBackground(newColor);
}
}

public void stateChanged(ChangeEvent e) {
Color newColor1 = cc1.getColor();
//Color newColor2 = cc2.getColor();
banner.setForeground(newColor1);
//banner.setForeground(newColor2);
}

/**
* Create the GUI and show it.  For thread safety,
* this method should be invoked from the
* event-dispatching thread.
*/
private static void createAndShowGUI() {
//Create and set up the window.
JFrame frame = new JFrame("ColorChooser");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//Create and set up the content pane.
JComponent newContentPane = new ColorChooser ();
newContentPane.setOpaque(true); //content panes must be opaque
frame.setContentPane(newContentPane);

//Display the window.
frame.pack();
frame.setVisible(true);
}



}
