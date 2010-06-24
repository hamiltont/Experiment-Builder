/**
 * 
 */
package edu.vanderbilt.psychology.gui.sideBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.MainSideBar;
import edu.vanderbilt.psychology.gui.main.MainStageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;

/**
 * @author Hamilton Turner
 * 
 */
// TODO Allow an individual element to override the default PreviewPanel
public class PreviewPanel extends JPanel {

	public static final int height_ = 200;
	private static final Dimension preferredSize_ = new Dimension(
			Short.MAX_VALUE, height_);
	private static final Dimension minimumSize_ = new Dimension(
			MainSideBar.width_, height_);

	private SlideElement currentElement_ = null;

	private JLabel name_;

	private MainStageWrapper stage_;

	public PreviewPanel(MainStageWrapper stage) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Border insideBorder = BorderFactory.createEmptyBorder(0, 10, 0, 0);
		Border outsideBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.GRAY);
		setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		
		setPreferredSize(preferredSize_);
		setMinimumSize(minimumSize_);
		setMaximumSize(preferredSize_);
		
		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		add(nameLabel);

		stage_ = stage;

		name_ = new JLabel("Test");
		name_.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		add(name_);

		JButton remove = new JButton("Remove Element");
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (currentElement_ == null)
					return;
				
				Rectangle bounds = currentElement_.getBounds();
				stage_.remove(currentElement_);
				currentElement_ = null;
				SelectionManager.getInstance().clearSelection();
				
				stage_.repaint(bounds);
				System.gc();
			}
		});
		add(remove);

		setVisible(true);
	}

	protected void paintComponent( Graphics g ) 
	{
		Graphics2D g2d = (Graphics2D)g;
		
	    int w = getWidth();
	    int h = getHeight();
	    
	    Color color1 = getBackground().brighter();
	    Color color2 = color1.brighter();
	     
	    // Paint a gradient from top to bottom
	    GradientPaint gp = new GradientPaint(
	        0, 0, color1,
	        0, h, color2);

	    g2d.setPaint(gp);
	    g2d.fillRect(0,0,w,h);
	 
	    setOpaque(true);
	}

	
	public void updatePreview(SlideElement element) {
		if (element == null) {
			// TODO - remove the remove button, reset all of the fields to
			// default values
			
			name_.setText("Nothing Selected");
			name_.invalidate();
			
			return;
		}
		currentElement_ = element;
		name_.setText(element.getElementName());
		name_.invalidate();
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -2812933657086452761L;
}
