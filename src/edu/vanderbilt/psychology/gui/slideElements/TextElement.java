/**
 * 
 */
package edu.vanderbilt.psychology.gui.slideElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.elements.TextModelElement;
import edu.vanderbilt.psychology.model.properties.Appearance;
import edu.vanderbilt.psychology.model.properties.MouseActions;
import edu.vanderbilt.psychology.model.properties.Movement;
import edu.vanderbilt.psychology.model.properties.Position;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * @author Hamilton Turner
 * 
 */
public class TextElement extends SlideElement {
	private JLabel label_;

	private ArrayList<Property> properties_;

	private TextModelElement mModel;

	public TextElement(TextModelElement model) {
		super();
		initializeWithModel(model);
		
	}
	
	public TextElement(String text, Font font, Color foreGround) {
		super();

		properties_ = new ArrayList<Property>();

		// Add properties
		properties_.add(new Appearance());
		properties_.add(new Movement());
		properties_.add(new Position());
		properties_.add(new MouseActions());

		label_ = new JLabel(text);
		label_.setFont(font);
		label_.setForeground(foreGround);
		add(label_);

		Dimension size = label_.getPreferredSize();
		label_.setSize(size);
		setBounds(0, 0, size.width, size.height);

		setOpaque(false);

		mModel = new TextModelElement(this);
	}
	
	@Override
	public Color getForeground() {
		if (label_ != null)
			return label_.getForeground();
		return super.getForeground();
	}

	public String getText() {
		return label_.getText();
	}

	@Override
	public Font getFont() {
		if (label_ != null)
			return label_.getFont();
		return super.getFont();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(new Color(204, 204, 204));
		g.drawLine(0, 0, label_.getWidth(), 0);
		g.drawLine(label_.getWidth() - 1, 0, label_.getWidth() - 1, label_
				.getHeight());
		g.drawLine(label_.getWidth(), label_.getHeight() - 1, 0, label_
				.getHeight() - 1);
		g.drawLine(0, label_.getHeight(), 0, 0);
	}

	@Override
	// TODO - make sure this works
	public void initializeWithModel(ModelElement model) {
		if (!(model instanceof TextModelElement))
			throw new IllegalArgumentException(
					"Attempted to initialize a TextElement with a model that was not an instance of TextModelElement");

		TextModelElement tme = (TextModelElement) model;
		properties_ = (ArrayList<Property>) tme.getProperties();

		label_ = new JLabel(tme.getText());
		label_.setFont(tme.getFont());
		label_.setForeground(tme.getForeGround());

		// TODO is this doing anything? We are not adding this to the
		// JLayeredpane, so the integer doesn't have any significance
		add(label_, tme.getLayer());

		Dimension size = label_.getPreferredSize();
		label_.setSize(size);
		setBounds(0, 0, size.width, size.height);

		setLocation(tme.getLocation());
		setOpaque(false);
		
		mModel = tme;
	}

	@Override
	public String getElementName() {
		return label_.getText();
	}

	@Override
	public ModelElement getModel() {
		return mModel;
	}

	@Override
	public List<Property> getProperties() {
		// TODO this is a hack for now. Later on refactor this so that the
		// properties are stored in the model
		return properties_;
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -6668463377343694947L;

}
