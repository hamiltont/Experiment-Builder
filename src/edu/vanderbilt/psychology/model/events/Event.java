package edu.vanderbilt.psychology.model.events;

import javax.swing.JComponent;

import edu.vanderbilt.psychology.model.MutableInt;
import edu.vanderbilt.psychology.model.elements.ModelElement;

public class Event {

	private EventType mType;
	private int mEventActionCode;
	private ModelElement mModel;
	private Object mData;

	/**
	 * 
	 * @param type
	 *            A general category that this event fits into
	 * @param eventAction
	 *            An integer that describes the action that should be performed
	 *            by the receiver of this event. This only has meaning to the
	 *            party receiving events of this type
	 * @param data
	 *            Some data that can be used by the {@link EventListener}. This
	 *            data only has meaning for the {@link EventListener}
	 */
	public Event(EventType type, int eventAction, ModelElement model,
			Object data) {
		mType = type;
		mEventActionCode = eventAction;
		mModel = model;
		mData = data;
	}

	public EventType getType() {
		return mType;
	}
	
	public Object getData() {
		return mData;
	}

	public JComponent getSource() {
		return mModel.getJComponent(new MutableInt());
	}

	public int getActionCode() {
		return mEventActionCode;
	}
}
