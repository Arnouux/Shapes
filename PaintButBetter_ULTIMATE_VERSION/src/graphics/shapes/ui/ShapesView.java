package graphics.shapes.ui;

import graphics.shapes.SCollection;
import graphics.ui.View;

import java.awt.Graphics;
import graphics.shapes.ui.ShapeDraftman;

public class ShapesView extends View {

	private SCollection model;
	private ShapesController controller;
	
	public ShapesView(SCollection model) {
		super(model);
		this.model = (SCollection) model;
		this.controller = defaultController(model);
		this.controller.setView(this);
		
		this.setFocusable(true); // for KeyListening
	}
	

	
	@Override
	public void setModel(Object model)
	{
		this.model = (SCollection) model;
		this.controller.setModel((SCollection) model);
	}
	
	@Override
	public SCollection getModel()
	{
		return this.model;
	}
	
	@Override
	public ShapesController defaultController(Object model) {
		return new ShapesController(model);
	}
	
	public void invalidate() {
		this.paintImmediately(getBounds());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		SCollection model = (SCollection)this.getModel();
		
		if (model == null) { return; }
		
		ShapeDraftman sd = new ShapeDraftman(g);
		model.accept(sd);
	}
	
	
	
}
