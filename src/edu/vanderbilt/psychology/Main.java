package edu.vanderbilt.psychology;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
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

		final ThreeDimensionalStrategy tds = new ThreeDimensionalStrategy();
		tds.prepare(file.getCards(), file.getDistractor());

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				JFrame f = new JFrame();
				Dimension screenSize = Toolkit.getDefaultToolkit()
						.getScreenSize();
				f.setBounds(0, 0, screenSize.width, screenSize.height);

				f.setVisible(true);

				tds.playNextGame(f);

			}
		});

	}

}
