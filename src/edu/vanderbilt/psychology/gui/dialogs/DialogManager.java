package edu.vanderbilt.psychology.gui.dialogs;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 * Provides an interface to interact with the appropriate {@link JDialog}s.
 * {@link DialogManager} caches {@link JDialog}s, handles lazy vs greedy
 * instantiation, ensures that the same dialog is not created twice, and makes
 * sure the appropriate {@link JDialog} is active and on top when it's 'show'
 * method is called
 * 
 * @author hamiltont
 * 
 */
public class DialogManager {
	private static JDialog mActionDialog;
	private static JFileChooser mSaveFileChooser;

	public static void showActionDialog() {
		if (mActionDialog == null)
			mActionDialog = DialogBuilder.buildShowActionDialog();

		if (mActionDialog.isShowing() && mActionDialog.isActive())
			return;

		mActionDialog.setVisible(true);
		mActionDialog.requestFocus();
	}
}
