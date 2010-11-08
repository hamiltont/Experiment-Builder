package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.vanderbilt.psychology.gui.toolBar.CreateListDialog;

@SuppressWarnings("serial")
public class CreateListAction extends AbstractAction {

	public CreateListAction() {
		super("Create List");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Via some magic juju supplied by swing, execution will pause on this
		// line until the JDialog CreateListDialog is disposed
		@SuppressWarnings("unused")
		CreateListDialog dialog = new CreateListDialog();

		
	}

}
