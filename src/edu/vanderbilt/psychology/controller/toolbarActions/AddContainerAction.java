package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.filechooser.FileFilter;

import edu.vanderbilt.psychology.gui.main.MainStageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.UnusedImagePanel_OnlyForReference;

//TODO Container class needs to create a new container object 
//and does not currently do so
public class AddContainerAction extends AbstractAction {
	
	private MainStageWrapper stage_;

	public AddContainerAction(MainStageWrapper stage) {
		super("Add Container");
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ContainerFilter());

		int returnVal = fc.showOpenDialog(stage_);

		switch (returnVal) {
		case JFileChooser.APPROVE_OPTION:
			approve(fc.getSelectedFile());
			break;
		case JFileChooser.ERROR_OPTION:
			error();
			break;
		default:
		case JFileChooser.CANCEL_OPTION:
			break;
		}
	}

	private void approve(File file) {
		UnusedImagePanel_OnlyForReference p = new UnusedImagePanel_OnlyForReference();
		p.setVisible(true);
		stage_.add(p, JLayeredPane.PALETTE_LAYER); // TODO - decide
		// which layer
		// we would like
		// to be adding stuff on here

	}

	private void error() {

	}

	private class ContainerFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			String extension = Utils.getExtension(f);
			// TODO Extensions Don't Matter in this case. Remove Later
			if (extension != null) {
				if (extension.equals(Utils.avi)) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}

		@Override
		public String getDescription() {
			return "Supported Containers";
		}

	}
	private static final long serialVersionUID = 6047074514562300162L;
}
