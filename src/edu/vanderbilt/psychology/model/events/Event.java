package edu.vanderbilt.psychology.model.events;

import javax.swing.JComponent;

import edu.vanderbilt.psychology.model.MutableInt;
import edu.vanderbilt.psychology.model.elements.ModelElement;

public class Event {

	private EventType mType;
	private int mEventActionCode;
	private ModelElement mModel;

	/**
	 * 
	 * @param type
	 *            A general category that this event fits into
	 * @param eventAction
	 *            An integer that describes the action that should be performed
	 *            by the receiver of this event. This only has meaning to the
	 *            party receiving events of this type
	 */
	public Event(EventType type, int eventAction, ModelElement model) {
		mType = type;
		mEventActionCode = eventAction;
		mModel = model;
	}

	public EventType getType() {
		return mType;
	}
	
	public JComponent getSource() {
		return mModel.getJComponent(new MutableInt());
	}
	
	public int getActionCode() {
		return mEventActionCode;
	}
}
