package edu.vanderbilt.psychology.model.reactor;

import javax.swing.JComponent;

import edu.vanderbilt.psychology.model.MutableInt;
import edu.vanderbilt.psychology.model.elements.ModelElement;

/**
 * Represents an action that should be caused to occur. This is typically sent
 * in response to a {@link Trigger}
 * 
 * @author hamiltont
 * 
 */
public class Action {

	private ActionType mType;
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
	 *            Some data that can be used by the {@link ActionListener}. This
	 *            data only has meaning for the {@link ActionListener}
	 */
	public Action(ActionType type, int eventAction, ModelElement model,
			Object data) {
		mType = type;
		mEventActionCode = eventAction;
		mModel = model;
		mData = data;
	}

	public ActionType getType() {
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
