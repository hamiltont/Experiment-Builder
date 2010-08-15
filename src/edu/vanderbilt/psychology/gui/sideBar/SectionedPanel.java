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
 * Creates the properties panel in the sidebar. This is the part of the sidebar
 * below the {@link PreviewPanel}.
 * 
 * This panel has multiple sections that help to divide the panel into usable
 * groupings. TODO In version 2, each section will be collapsable, so that non
 * relevant sections can be hidden from the user. The best way to understand
 * this panel is by imaging a list view, where the elements in the list are as
 * follows:
 * <ul>
 * <li> {@link SectionDivider}</li>
 * <li> {@link Section}</li>
 * <li> {@link Section}</li>
 * <li>any other {@link Section}s in this group</li>
 * <li> {@link SectionDivider}</li>
 * <li> {@link Section}</li>
 * <li> {@link Section}</li>
 * <li>any other {@link Section}s in this group</li>
 * <li>any other groups</li>
 * </ul>
 * 
 * As indicated, each grouping is prefixed with a {@link SectionDivider}, and
 * under the {@link SectionDivider} are the multiple {@link Section}s that are
 * logically contained in that grouping.
 * 
 * <p>
 * {@link SectionedPanel} is laid out using a {@link BoxLayout}. The
 * organization is as follows:
 * </p>
 * <img src=
 * "../../../../../../doc-source/diagrams/sidebar-properties-panel-box-layout.jpg"
 * alt="Application main BorderLayout" /><br />
 * <p>
 * The teal, purple, and green bordered boxes represent {@link SectionDivider}s
 * and {@link Section}s, while the red bordered box represents any extra space.
 * The {@link BoxLayout} respects the declared maximum width of the internal
 * elements, so in order to have the {@link SectionDivider}s and the
 * {@link Section}s expand to take up all available horizontal space, the
 * {@link Section}s and {@link SectionDivider}s must have their maximum width
 * set to {@link Short#MAX_VALUE}, which will cause the {@link BoxLayout} to
 * expand their widths appropriately.
 * </p>
 * <p>
 * The {@link BoxLayout} will attempt to give each element its preferred height.
 * If the sum of the preferred heights does not equal the available height, then
 * the {@link BoxLayout} will increase the size of the internal components until
 * all available space is taken. However, the {@link BoxLayout} respects the
 * declared maximum size of the components, and will not make them taller than
 * their declared maximum height. Therefore, {@link SectionDivider}s and
 * {@link Section}s should always set their maximum heights to a reasonable
 * value in order to avoid being resized.
 * </p>
 * <p>
 * Additionally, {@link Section}s and {@link SectionDivider}s will be decreased
 * in height if there is not enough available room to give the components their
 * preferred heights. However, they will only be decreased to the declared
 * minimum height. If the available size continues to shrink, the components
 * will begin disappearing. This indicates that a {@link Section} or
 * {@link SectionDivider} may want to set it's minimum size appropriately so as
 * not to be decreased in height beyond recognition. The minimum size is a
 * secondary concern when using a {@link BoxLayout}, because by default it
 * attempts to expand and use all available space. Declaring the maximum size
 * tends to be much more necessary that declaring the minimum size.
 * </p>
 * 
 * <p>
 * TODO This is currently a {@link BoxLayout} wrapped by another
 * {@link BoxLayout}. Kind of wish I could get rid of the useless redirection
 * here and simply manage the logical organization of components within the main
 * {@link BoxLayout}. This is something that can be done later, as the current
 * setup is working and would be a hassle to change
 * </p>
 * 
 * @author Hamilton Turner
 * 
 */
// TODO Current thoughts: This will have a few sections, with section headers
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
