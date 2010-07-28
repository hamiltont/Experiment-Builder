package edu.vanderbilt.psychology;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.thoughtworks.xstream.XStream;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		XStream xs = new XStream();
		String dataXml = "methodCards.xml";
		File f = new File(dataXml);
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		XMLFile file = (XMLFile) xs.fromXML(fs);
		file.getCards();

		tds.prepare(file.getCards(), file.getDistractor());

		SwingUtilities.invokeLater(chooseFilename);

	}

	final static ThreeDimensionalStrategy tds = new ThreeDimensionalStrategy();

	final static Runnable chooseFilename = new Runnable() {

		@Override
		public void run() {

			nameFrame = new JFrame();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			int width = 370;
			int height = 80;

			nameFrame.setBounds(screenSize.width / 2 - width / 2,
					screenSize.height / 2 - height / 2, width, height);
			nameFrame.setResizable(false);

			JPanel main = new JPanel();
			main.setLayout(new BorderLayout());
			instructionsLabel = new JLabel("    Please choose a filename");
			main.add(instructionsLabel, BorderLayout.NORTH);

			JPanel bottomBar = new JPanel(
					new FlowLayout(FlowLayout.LEFT, 10, 5));
			filenameField = new JTextField(20);
			bottomBar.add(filenameField);
			JButton submit = new JButton("submit");
			bottomBar.add(submit);

			submit.addMouseListener(new SubmitFileName());

			main.add(bottomBar, BorderLayout.SOUTH);
			nameFrame.add(main);
			nameFrame.setVisible(true);

			// SwingUtilities.invokeLater(mainRunnable);

		}
	};

	private static JLabel instructionsLabel;
	private static JTextField filenameField;
	private static JFrame nameFrame;

	static class SubmitFileName extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			String filename = filenameField.getText().trim();

			if (filename.endsWith(".xls") == false)
				filename += ".xls";

			File f = new File(filename);
			if (f.exists()) {
				instructionsLabel.setText("   '" + filename
						+ "' already exists. Please enter a new filename");

				return;
			}

			// In case we added .xls
			filenameField.setText(filename);

			// Dismiss frame...
			nameFrame.dispose();

			SwingUtilities.invokeLater(mainRunnable);
		}
	}

	final static Runnable mainRunnable = new Runnable() {

		@Override
		public void run() {

			JFrame f = new JFrame();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			f.setBounds(0, 0, screenSize.width, screenSize.height);

			f.setVisible(true);

			tds.setFilename(filenameField.getText());
			tds.playNextGame(f);

		}
	};

}
