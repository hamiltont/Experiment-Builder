/**
 * 
 */
package edu.vanderbilt.psychology.gui.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author Hamilton Turner
 * 
 */
public class MainMenuBar extends JMenuBar implements ActionListener {

	public MainMenuBar(MainFrame frame) {
		// Set up the lone menu.
		JMenu menu = new JMenu("Document");
		menu.setMnemonic(KeyEvent.VK_D);
		add(menu);

		// Set up the first menu item.
		JMenuItem menuItem = new JMenuItem("New");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("new");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Set up the second menu item.
		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	// React to menu selections.
	public void actionPerformed(ActionEvent e) {
		if ("new".equals(e.getActionCommand())) { // new
			//createFrame();
		} else { // quit
			quit();
		}
	}

	// Quit the application.
	protected void quit() {
		System.exit(0);
	}

	// Create a new internal frame.
	protected void createFrame() {
//		EBInternalFrame frame = new EBInternalFrame();
//		frame.setVisible(true); // necessary as of 1.3
//		mainFrame_.add(frame);
//		try {
//			frame.setSelected(true);
//		} catch (java.beans.PropertyVetoException e) {
//		}
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 7414120822927242907L;

}
