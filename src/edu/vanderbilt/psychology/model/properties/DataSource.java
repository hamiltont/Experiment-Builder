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
import edu.vanderbilt.psychology.model.Event;

/**
 * This {@link Property} will define the source of data for a
 * {@link SlideElement}. This could include a single {@link String} if the
 * {@link SlideElement} is a {@link AddImageAction}, or an {@link List} of some
 * sort for more complex {@link SlideElement}s, such as
 * {@link AddContainerAction}
 * 
 * @author Hamilton Turner
 * 
 */
public class DataSource extends Property {
	private List<File> files_ = new ArrayList<File>();
	private Type type_;
	
	private static DataSource defaultDataSource_;

	public enum Type {
		Single_File, Multiple_Files
	};

	private DataSource() {
		// setup default values
	}
	
	public DataSource(File singleFile) {
		files_.add(singleFile);
		type_ = Type.Single_File;
	}
	
	public DataSource(List<File> files) {
		files_.addAll(files);
		type_ = Type.Multiple_Files;
	}

	@Override
	public Object clone() {
		return new DataSource();
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
