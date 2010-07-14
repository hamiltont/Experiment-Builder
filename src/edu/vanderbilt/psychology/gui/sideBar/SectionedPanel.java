/**
 * 
 */
package edu.vanderbilt.psychology.gui.sideBar;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.DataCapture;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * 
 * This controls the part of the {@link SideBar} below the
 * {@link PreviewPanel}. It lays out multiple {@link Section} elements, and adds
 * a few {@link DividerPanel} elements in between the {@link Section} elements
 * to make the UI more intuitive
 * 
 * @author Hamilton Turner
 * 
 */
// NOTE Current thoughts: This will have a few sections, with section headers
// being instances of {@link DividerPanel}. For now, there is a {@link
// DividerPanel} called Element Properties, and a {@link DividerPanel} called
// General Properties. I will likely change this at a later time to say
// something like "Inputs", "Slide Properties" and "Experiment Properties"
public class SectionedPanel extends JPanel {
	
	/**
	 * Holds the dynamically changing list of Element properties
	 */
	private JPanel currentElementPropertiesPanel_;

	/**
	 * The {@link Section}s describing {@link Property}s for the selected
	 * {@link SlideElement}
	 */
	private List<Section> currentElementPropertySections_ = new ArrayList<Section>();

	/** Any inputs that should always be showing */
	private List<Section> inputs_;

	/**
	 * The {@link DataCapture} should always be showing in this
	 * {@link SectionedPanel}
	 */
	private Section dataCapture_;

	public SectionedPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Add the element props divider
		SectionDivider eProps = new SectionDivider("Element Properties");

		eProps.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		eProps.setAlignmentX(CENTER_ALIGNMENT);

		add(eProps);

		// Add the element props section
		currentElementPropertiesPanel_ = new JPanel();
		currentElementPropertiesPanel_.setLayout(new BoxLayout(
				currentElementPropertiesPanel_, BoxLayout.PAGE_AXIS));
		currentElementPropertiesPanel_.setAlignmentX(CENTER_ALIGNMENT);

		add(currentElementPropertiesPanel_);

		// Add the general props divider
		SectionDivider gProps = new SectionDivider("General Properties");

		gProps.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		gProps.setAlignmentX(CENTER_ALIGNMENT);
		add(gProps);

		setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
		setVisible(true);

		// Add the general props section
		// TODO - update this

	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int w = getWidth();
		int h = getHeight();

		Color color1 = getBackground();
		Color color2 = color1.brighter();

		// Paint a gradient from top to bottom
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);

		setOpaque(false);
		super.paintComponent(g);
		setOpaque(true);
	}

	public void updatePropertiesList(SlideElement element) {

		for (Section s : currentElementPropertySections_)
			currentElementPropertiesPanel_.remove(s);

		currentElementPropertySections_.clear();

		if (element == null) {
			validate();
			return;
		}

		List<Property> props = element.getProperties();

		if (props == null) {
			validate();
			return;
		}

		for (Property p : props)
			currentElementPropertySections_.add(p.getSection());

		drawElementPropertyPanels();
	}

	/**
	 * Draws all {@link Section}s currently stored in
	 * currentElementPropertySections_
	 */
	private void drawElementPropertyPanels() {
		for (Section s : currentElementPropertySections_)
			currentElementPropertiesPanel_.add(s);

		validate();
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 3764137142368653591L;
}
