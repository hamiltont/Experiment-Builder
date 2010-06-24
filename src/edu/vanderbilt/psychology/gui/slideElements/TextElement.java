/**
 * 
 */
package edu.vanderbilt.psychology.gui.slideElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

import edu.vanderbilt.psychology.model.elements.TextElementModel;
import edu.vanderbilt.psychology.model.properties.Appearance;
import edu.vanderbilt.psychology.model.properties.Movement;
import edu.vanderbilt.psychology.model.properties.Position;

/**
 * @author Hamilton Turner
 * 
 */
public class TextElement extends SlideElement {
	private JLabel label_;

	public TextElement(String text, Font font, Color foreGround) {
		super();

		// Add properties
		properties_.add(Appearance.getDefaultAppearance());
		properties_.add(Movement.getDefaultMovement());
		properties_.add(Position.getDefaultPosition());

		label_ = new JLabel(text);
		label_.setFont(font);
		label_.setForeground(foreGround);
		add(label_);

		Dimension size = label_.getPreferredSize();
		label_.setSize(size);
		setBounds(0, 0, size.width, size.height);

		setOpaque(false);
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

	public String getElementName() {
		return label_.getText();
	}

	@Override
	public TextElementModel getModel() {
		final TextElementModel model = new TextElementModel(this);
		return model;
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -6668463377343694947L;

}
