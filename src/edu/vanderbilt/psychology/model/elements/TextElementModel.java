/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import edu.vanderbilt.psychology.gui.slideElements.TextElement;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * @author Hamilton Turner
 *
 */
public class TextElementModel extends ModelElement {
	private String text_;
	private Font font_;
	private Color foreGround_;
	private List<Property> properties_;
	
	public TextElementModel(TextElement textElement) {
		text_ = textElement.getText();
		font_ = textElement.getFont();
		foreGround_ = textElement.getForeground();
		properties_ = textElement.getProperties();
	}
}
