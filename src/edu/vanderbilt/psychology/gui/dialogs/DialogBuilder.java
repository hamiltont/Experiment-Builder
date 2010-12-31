package edu.vanderbilt.psychology.gui.dialogs;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Builds {@link JDialog}s for various uses throughout the application
 * 
 * @author hamiltont
 * 
 */
public class DialogBuilder {

	/**
	 * Creates a JDialog that allows the user to select potential actions
	 */
	// TODO This dialog does not properly resize. Additionally, it should really
	// have a better data model than statically building in the possible
	// actions. Also, it may be unclear to some people that actions have
	// 'targets' e.g. a 'show' action would show a specific target. Some actions
	// have pre-defined targets e.g. slide actions always target the current
	// slide
	public static JDialog buildShowActionDialog() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Actions");
		DefaultMutableTreeNode slide = new DefaultMutableTreeNode(
				"Slide Actions");
		DefaultMutableTreeNode vis = new DefaultMutableTreeNode(
				"Visibility Actions");
		root.add(slide);
		root.add(vis);

		DefaultMutableTreeNode ns = new DefaultMutableTreeNode("Next Slide");
		slide.add(ns);
		slide.add(new DefaultMutableTreeNode("Previous Slide"));

		vis.add(new DefaultMutableTreeNode("Hide"));
		vis.add(new DefaultMutableTreeNode("Show"));

		JTree tree = new JTree(root);
		tree.makeVisible(new TreePath(ns.getPath()));

		JOptionPane pane = new JOptionPane(tree, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);

		return pane.createDialog("Possible Actions");
	}
}
