package edu.vanderbilt.psychology.gui.toolBar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Builds and displays the create list dialog. Currently also handles building
 * and showing and sub-dialogs that may result from interacting with the create
 * list dialog.
 * 
 * @author hamiltont
 * @contributor sethfri
 * 
 */
@SuppressWarnings("serial")
public class CreateListDialog extends JDialog {

	public static final int RESULT_OK = 1 << 0;
	public static final int RESULT_CANCEL = 1 << 1;

	public CreateListDialog() {
		super((JFrame) null, "Create List", true);

		JPanel ui = buildStepOneUI();
		setContentPane(ui);
		pack();

		setVisible(true);
	}

	private JPanel buildStepOneUI() {
		JPanel ui = new JPanel(new BorderLayout());
		JLabel instructions = new JLabel(
				"What kind of data will this list hold?");
		ui.add(instructions, BorderLayout.NORTH);

		ButtonGroup bg = new ButtonGroup();
		JRadioButton files = new JRadioButton(
				"Files (Image files, Audio files, etc)");
		JRadioButton strings = new JRadioButton("Strings");
		JRadioButton numbers = new JRadioButton("Numbers");
		JRadioButton lists = new JRadioButton("Other lists");

		bg.add(files);
		bg.add(strings);
		bg.add(numbers);
		bg.add(lists);
		bg.setSelected(files.getModel(), true);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(files);
		buttons.add(strings);
		buttons.add(numbers);
		buttons.add(lists);

		ui.add(buttons, BorderLayout.CENTER);

		// Building the bottom panel for the continue and cancel buttons
		JPanel continueCancel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(continueListener_);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		continueCancel.add(cancel);
		continueCancel.add(continueButton);

		ui.add(continueCancel, BorderLayout.SOUTH);

		return ui;
	}

	private ActionListener continueListener_ = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();

			JDialog listDialog = new JDialog();

			JPanel ui = buildTextListUI(listDialog);

			listDialog.setContentPane(ui);
			listDialog.pack();
			listDialog.setVisible(true);

		}
	};

	private JPanel buildTextListUI(final JDialog parentDialog) {
		JPanel ui = new JPanel(new BorderLayout());

		// Creating the top panel with the entry pane
		JPanel textEntryPanel = new JPanel(new FlowLayout());
		JTextField entryTextField = new JTextField(
				"Type strings and hit enter to put them in the list");
		JButton enter = new JButton("Enter");
		textEntryPanel.add(entryTextField);
		textEntryPanel.add(enter);
		ui.add(textEntryPanel, BorderLayout.NORTH);

		// Creating the middle panel with the list and remove items
		JPanel listArea = new JPanel(new FlowLayout());

		JList list = new JList();
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listPane = new JScrollPane(list);
		JButton remove = new JButton("Remove String");
		listArea.add(listPane);
		listArea.add(remove);

		ui.add(listArea, BorderLayout.CENTER);

		// Creating the bottom panel with the cancel and ok buttons
		JPanel cancelOk = new JPanel(new FlowLayout());

		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				parentDialog.dispose();
			}
		});

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				parentDialog.dispose();
			}

		});

		cancelOk.add(cancel);
		cancelOk.add(ok);

		ui.add(cancelOk, BorderLayout.SOUTH);

		return ui;
	}
}
