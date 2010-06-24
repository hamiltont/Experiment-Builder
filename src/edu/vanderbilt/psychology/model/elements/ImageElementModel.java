/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.util.List;

import edu.vanderbilt.psychology.gui.slideElements.ImageElement;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * @author Hamilton Turner
 *
 */
public class ImageElementModel extends ModelElement {
	private List<Property> properties_;
	
	public ImageElementModel(ImageElement imageElement)
	{
		properties_ = imageElement.getProperties();
	}
}
