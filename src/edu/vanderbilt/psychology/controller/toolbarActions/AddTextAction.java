package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLayeredPane;

import edu.vanderbilt.psychology.controller.toolbarActions.util.FontChooser;
import edu.vanderbilt.psychology.controller.toolbarActions.util.FontChooser.Result;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.TextElement;

/**
 * 
 * @author Grayson Sharpe
 * 
 */
public class AddTextAction extends AbstractAction {

	private StageWrapper stage_;

	public AddTextAction(StageWrapper stage) {
		super("Add Text");
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		// Via some magic juju supplied by swing, execution will pause on this
		// line until the JDialog FontChooser is disposed
		FontChooser fc = new FontChooser();

		Result r = fc.getResult();
		if (r == Result.OK) {

			TextElement te = new TextElement(fc.getText(), fc.getFont(), fc
					.getColor());
			stage_.add(te, JLayeredPane.PALETTE_LAYER);
		}

	}

	private static final long serialVersionUID = 571492674596081599L;
}
