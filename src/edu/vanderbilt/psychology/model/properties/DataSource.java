/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.controller.toolbarActions.AddContainerAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddImageAction;
import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.events.Event;

/**
 * This {@link Property} will define the source of data for a
 * {@link SlideElement}. This could include a single {@link String} if the
 * {@link SlideElement} is a {@link AddImageAction}, or an {@link List} of some
 * sort for more complex {@link SlideElement}s, such as
 * {@link AddContainerAction}.
 * 
 * Internally all data is represented as a string, only by understanding the
 * type of data can the string be properly interpreted. If type is a file type,
 * then the string is the path to the file
 * 
 * @author Hamilton Turner
 * 
 */
public class DataSource extends Property {
	private List<String> files_ = new ArrayList<String>();
	private Type type_;

	private int mCurrentArrayPosition = 0;

	public enum Type {
		Single_File, Multiple_Files
	};

	private DataSource() {
		// setup default values
	}

	public DataSource(String singleFilePath) {
		files_.add(singleFilePath);
		type_ = Type.Single_File;
	}

	public DataSource(List<String> filePaths) {
		files_.addAll(filePaths);
		type_ = Type.Multiple_Files;
	}

	public String getCurrentData() {
		return files_.get(mCurrentArrayPosition);
	}

	@Override
	public Section getSection() {
		JPanel poo = new JPanel();
		poo.add(new JLabel("Hi there!!!"));
		return new Section("Data Source", poo);
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub

	}

}
