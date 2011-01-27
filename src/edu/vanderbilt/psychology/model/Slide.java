/**
 * 
 */
package edu.vanderbilt.psychology.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.sun.tools.javac.util.Pair;

import edu.vanderbilt.psychology.gui.main.Builder;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.inputs.Input;
import edu.vanderbilt.psychology.model.properties.Property;
import edu.vanderbilt.psychology.model.reactor.Reactor;

/**
 * {@link Slide}s do not directly keep track of any visual information. The GUI
 * keeps track of all {@link SlideElement}s and their associated
 * {@link Property}s. When an event occurs that will cause those GUI objects to
 * be destroyed, such as switching to the next slide or the application exiting,
 * the relevant {@link Property} information from those GUI objects is written
 * into the {@link Slide} object for storage. (Actually, a reference to
 * SlideElements are saved. Memory intensive, but it works for now)
 * 
 * <p>
 * A {@link Slide} can also define events that {@link Input}s and
 * {@link Property}s can listen for and react to. This allows, for example, the
 * mouse to be disabled when a {@link Slide} starts or data to be written to
 * file when a {@link Slide} completes
 * </p>
 * 
 * @author Hamilton Turner
 * 
 */
// TODO Add some visual info the Slide, particularly the z-ordering of the
// elements on screen (or add that to the Appearance element somehow)
public class Slide {

	public static final int ACTION_ADVANCE_TO_NEXT_SLIDE = 0;

	Set<ModelElement> elements_ = new HashSet<ModelElement>();
	List<Reactor> reactors_ = new ArrayList<Reactor>();

	private JLayeredPane mSlideThumbnail;

	public void saveElement(ModelElement me) {
		elements_.add(me);
	}

	public void addEventReactor(Reactor reactor) {
		reactors_.add(reactor);
	}

	public List<Reactor> getEventReactors() {
		return reactors_;
	}

	public void setEventReactors(List<Reactor> reactors) {
		reactors_ = reactors;
	}

	/**
	 * Given a {@link SlideElement}, this returns all of the {@link Reactor}s
	 * that are listening for some type of event on that {@link SlideElement}
	 * 
	 * @param element
	 * @return
	 */
	public List<Reactor> getReactorsReferencing(SlideElement element) {
		ArrayList<Reactor> result = new ArrayList<Reactor>(1);
		for (Reactor er : reactors_)
			if (er.getSlideElement() == element)
				result.add(er);

		return result;
	}

	private List<Reactor> getReactorsReferencing(ModelElement element) {
		ArrayList<Reactor> result = new ArrayList<Reactor>(1);
		for (Reactor er : reactors_)
			if (er.getModelElement() == element)
				result.add(er);

		return result;
	}

	public void clearElements() {
		elements_.clear();
	}

	public Set<ModelElement> getModelElements() {
		return elements_;
	}

	/**
	 * Converts the internal model of elements into a series of
	 * {@link JComponent}s that can then be added to the player's GUI. As it
	 * builds the list of {@link JComponent}s, it also registers any triggers as
	 * specified by {@link Reactor}s
	 * 
	 * @return A list of {@link JComponent}s that represent various
	 *         {@link ModelElement}s. Each list item is a {@link Pair}
	 *         containing the {@link JComponent} and an {@link Integer}
	 *         representing the layer it should be placed on in a
	 *         {@link JLayeredPane}
	 */
	public List<Pair<JComponent, Integer>> getGui() {
		ArrayList<Pair<JComponent, Integer>> components = new ArrayList<Pair<JComponent, Integer>>(
				elements_.size());

		Object[] elements = elements_.toArray();
		for (int i = 0; i < elements.length; i++) {

			MutableInt result = new MutableInt();
			JComponent output = ((ModelElement) elements[i])
					.getJComponent(result);

			// For each reactor that references this model element, add the
			// triggers to the component
			for (Reactor reactor : getReactorsReferencing((ModelElement) elements[i]))
				reactor.addTriggerToJComponent(output);

			components.add(new Pair<JComponent, Integer>(output, result
					.getValue()));
		}

		return components;
	}

	public void setSlideThumbnail(JLayeredPane thumbnail) {
		mSlideThumbnail = thumbnail;

		updateThumbnail();
	}

	public void updateThumbnail() {
		if (mSlideThumbnail == null)
			return;

		for (Component c : mSlideThumbnail.getComponents()) {
			if (false == c instanceof JLabel)
				continue;

			JLabel label = (JLabel) c;
			if (label.getIcon() == null)
				continue;

			BufferedImage backgroundImage = new BufferedImage(
					Builder.SLIDE_THUMBNAIL_WIDTH,
					Builder.SLIDE_THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_RGB);

			List<Pair<JComponent, MutableInt>> components = new ArrayList<Pair<JComponent, MutableInt>>();
			for (ModelElement me : elements_) {
				MutableInt mi = new MutableInt();
				JComponent foo = me.getJComponent(mi);
				Pair p = new Pair<JComponent, MutableInt>(foo, mi);
				components.add(p);
			}

			BufferedImage bgImage = new BufferedImage(1400, 900,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = bgImage.getGraphics();

			// You cannot just print each component, because it's location is
			// not relative to anything, so all the components will show up in
			// the top left with default alignments (typically left-aligned
			// horizontally and center-aligned vertically within their bounds).
			// To make sure all the components are drawn at the correct
			// locations we add them to a JPanel first
			JPanel renderPanel = new JPanel(null, false);
			renderPanel.setSize(1400, 900);
			renderPanel.setBackground(Color.LIGHT_GRAY);
			for (Pair<JComponent, MutableInt> pair : components)
			// TODO respect layer ordering
			{
				renderPanel.add(pair.fst);
			}

			// Now print the JPanel
			renderPanel.print(g);

			// Draw a border
			g.setColor(Color.black);
			g.drawRect(1, 1, 1400 - 1, 900 - 1);

			backgroundImage.getGraphics().drawImage(bgImage, 0, 0,
					Builder.SLIDE_THUMBNAIL_WIDTH,
					Builder.SLIDE_THUMBNAIL_WIDTH, 0, 0, 1400, 900, null);

			ImageIcon newIcon = new ImageIcon(backgroundImage);
			label.setIcon(newIcon);
		}
	}

	public JLayeredPane getSlideThumbnail() {
		return mSlideThumbnail;
	}
}
