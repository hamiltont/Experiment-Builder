package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLayeredPane;

import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.TextElement;

/**
 * 
 * @author Grayson Sharpe
 *
 */
public class AddTextAction extends AbstractAction {
	
	private StageWrapper stage_;
	final FontChooser fc = new FontChooser();

	public AddTextAction(StageWrapper stage) {
		super("Add Text");
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO - Right now we have no way of knowing if the user hit cancel.
		// FontChooser needs to be modified to add this ability
		approve(fc.showDialog());
	}

	private void approve(Font font) {

		// TODO - Make this load the correct textn after modifying the font interface
		TextElement te = new TextElement("Hello World", font, fc.getColor());

		stage_.add(te, JLayeredPane.PALETTE_LAYER);
	}
	private static final long serialVersionUID = 571492674596081599L;
}
