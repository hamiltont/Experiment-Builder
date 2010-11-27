/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.gui.slideElements.TextElement;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * @author Hamilton Turner
 *
 */
public class TextModelElement extends ModelElement {
	private String text_;
	private Font font_;
	private Color foreGround_;
	private List<Property> properties_;
	
	public TextModelElement(TextElement textElement) {
		text_ = textElement.getText();
		font_ = textElement.getFont();
		foreGround_ = textElement.getForeground();
		properties_ = textElement.getProperties();
	}
	
	public String getText() {
		return text_;
	}
	
	public Font getFont() {
		return font_;
	}
	
	public Color getForeGround() {
		return foreGround_;
	}
	
	public List<Property> getProperties() {
		return properties_;
	}

	@Override
	public SlideElement getInitializedSlideElement() {
		final TextElement te = new TextElement();
		te.initializeWithModel(this);
		return te;
	}
}