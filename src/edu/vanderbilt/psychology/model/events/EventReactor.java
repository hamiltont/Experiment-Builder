package edu.vanderbilt.psychology.model.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import com.sun.org.apache.xpath.internal.operations.Mod;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.player.EventManager;

/**
 * An event reactor that will send an {@link Event} to the {@link EventManager}
 * in response to some trigger, such as
 * {@link EventReactor#TRIGGER_ON_MOUSE_ENTER}.
 * 
 * 
 * This is created by the GUI code, and therefore contains a reference to a
 * {@link SlideElement} and the {@link Event} to fire. Before it is saved to
 * disk, however, the method {@link EventReactor#setModelElement(ModelElement)}
 * must be called to link this to an instance of a {@link ModelElement} that
 * corresponds to the {@link SlideElement} referenced by
 * {@link EventReactor#getSlideElement()}. Then, when this element is
 * deserialized, the {@link ModelElement} reference contained in this object can
 * be used to link this with the appropriately created {@link JComponent}.
 * 
 * @author hamiltont
 * 
 */
// TODO - add in conditions. See issue #7
public class EventReactor implements EventListener {

	public static final int TRIGGER_ON_MOUSE_ENTER = 1 << 0;

	private SlideElement mSlideElement;
	private JComponent mComponent;
	private ModelElement mModelElement;
	private Event mEventToFire;
	private int mTrigger;

	public EventReactor(SlideElement element, Event eventToFire,
			int triggerOfInterest) {
		mSlideElement = element;
		mEventToFire = eventToFire;

		// Ensure that the trigger exists
		if (triggerOfInterest != TRIGGER_ON_MOUSE_ENTER)
			throw new IllegalArgumentException(
					"The provided trigger does not exist");

		mTrigger = triggerOfInterest;
	}

	/**
	 * Given a {@link JComponent} that presumably has just been created from a
	 * {@link ModelElement}, this adds the appropriate triggers to it based on
	 * what is contained within this {@link EventReactor}
	 * 
	 * 
	 * @param component
	 *            The component to add triggers to. Cannot be null
	 *            
	 * @see {@link EventReactor#TRIGGER_ON_MOUSE_ENTER}
	 * 
	 */
	public boolean addTriggerToJComponent(JComponent component) {
		switch (mTrigger) {
		case TRIGGER_ON_MOUSE_ENTER:
			component.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {

				}

				@Override
				public void mousePressed(MouseEvent arg0) {

				}

				@Override
				public void mouseExited(MouseEvent arg0) {

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					EventManager.getInstance().sendEvent(mEventToFire);
				}

				@Override
				public void mouseClicked(MouseEvent arg0) {

				}
			});
			break;
		default:
			throw new IllegalArgumentException(
					"The provided trigger is not recognized");
		}

		return true;
	}

	public SlideElement getSlideElement() {
		return mSlideElement;
	}

	public ModelElement getModelElement() {
		return mModelElement;
	}

	public void setModelElement(ModelElement me) {
		mModelElement = me;
	}

	@Override
	public void receiveEvent(Event e) {

	}

}
