/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.util.List;

import javax.swing.JComponent;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import edu.vanderbilt.psychology.gui.slideElements.ImageElement;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.MutableInt;
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

	@Override
	public SlideElement getInitializedSlideElement() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public JComponent getInitializedJComponent(MutableInt outputLayer) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}
}
