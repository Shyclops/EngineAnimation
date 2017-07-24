package animation.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import animation.model.Model;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Color[] colors = {
			Color.orange,
			Color.magenta,
			Color.red,
			Color.cyan
	};
	
	public Color green = new Color(0,128,0);
	public Color backgroundColor;
	
	// For buffering 
	private Graphics2D bufferGraphics;
	private Image offscreen;
	
    // Dimension of the frame
	private Dimension dim = new Dimension(550, 550); 
	
    // Position of the graphics
	private int x, y;
	
	Model model;
	
	public View(Model model) throws HeadlessException {
		this.model = model;

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		setSize(dim);
	    this.setVisible(true);
	    
	    this.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
            	init();
            }

			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentShown(ComponentEvent arg0) {}
        });
	}
	
	private void init () {
		
		
		dim = getSize();
    	x = dim.width / 2 - 40;
		y = 50; 
		
		// Create an offscreen image to draw on 
		offscreen = createImage(dim.width,dim.height); 
		
		// by doing this everything that is drawn by bufferGraphics 
        // will be written on the offscreen image.
        bufferGraphics = (Graphics2D)offscreen.getGraphics();
	}
	
	@Override
	public void paint(Graphics g) {
		
		if (bufferGraphics == null)
			return;
		
		// Wipe off everything that has been drawn before 
        // Otherwise previous drawings would also be displayed.
		bufferGraphics.clearRect(0, 0, dim.width, dim.width);
        
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("/home/cwon/Dropbox/ws/Engine_CDTProject/src/external_resources/map.png"));
		} catch (IOException e) {
			System.out.println("error");
		}
    	
    	// draw the offscreen image to the screen like a normal image.
        g.drawImage(img,0,0, 550, 550,this);
       
	}

}
