/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import edu.vanderbilt.psychology.gui.slideElements.ImageElement;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.MutableInt;
import edu.vanderbilt.psychology.model.properties.DataSource;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * @author Hamilton Turner
 * 
 */
public class ImageElementModel extends ModelElement {
	private List<Property> properties_;
	private Dimension mSize;
	private JComponent mComponent;

	public ImageElementModel(ImageElement imageElement) {
		properties_ = imageElement.getProperties();
		mSize = imageElement.getSize();
	}

	@Override
	public SlideElement getInitializedSlideElement() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public JComponent getJComponent(MutableInt outputLayer) {
		if (mComponent == null) {
			DataSource ds = null;
			for (Property p : properties_)
				if (p instanceof DataSource) {
					ds = (DataSource) p;
					break;
				}
			if (ds == null)
				throw new IllegalStateException(
						"An ImageElement must have a DataSource");

			String filename = ds.getCurrentData();
			JLabel image = new JLabel(new ImageIcon(filename));
			image.setLocation(getLocation());
			image.setSize(mSize);
			
			mComponent = image;
		}
		outputLayer.setValue(getLayer());

		return mComponent;
	}
}
