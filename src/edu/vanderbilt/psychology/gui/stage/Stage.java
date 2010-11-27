/**
 * 
 */
package edu.vanderbilt.psychology.gui.stage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.Experiment;

/**
 * <p>
 * Acts as the stage backdrop for the {@link StageWrapper}. Indicates the area
 * of the {@link StageWrapper} that correlates to the visible area of a slide
 * during an experiment.
 * </p>
 * 
 * <p>
 * Maintains a correct aspect ratio. Our two current options for aspect ratio
 * are 16:9 and 4:3. Currently, the height on this element is always set to be
 * 675px, so with 16:9 the width is 1200 and with 4:3 the width is 900. When
 * exporting the {@link Experiment}, and {@link SlideElement}s displayed outside
 * of this element should be trimmed (and hopefully a warning will pop up)
 * </p>
 * 
 * @author Hamilton Turner
 * 
 */
public class Stage extends JPanel {
	private boolean using_4_3 = true;

	public Stage() {
		super();

		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		setVisible(true);
	}

	@Override
	public void setSize(int maxWidth, int maxHeight) {
		int multiplier;
		Dimension d;
		if (using_4_3) {
			multiplier = Math.min(maxWidth / 4, maxHeight / 3);
			d = new Dimension(multiplier * 4, multiplier * 3);
		} else {
			multiplier = Math.min(maxWidth / 16, maxHeight / 9);
			d = new Dimension(multiplier * 16, multiplier * 9);
		}

		// Location set by StageWrapper so this can be centered
		super.setSize(d.width, d.height);
	}

	@Override
	public void setSize(Dimension d) {
		this.setSize((int) d.getWidth(), (int) d.getHeight());
	}

	private static final long serialVersionUID = -2467811192957373362L;
}
