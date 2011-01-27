/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.controller.toolbarActions.AddContainerAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddImageAction;
import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.EBList;
import edu.vanderbilt.psychology.model.reactor.Action;

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
	private String mData;
	private EBList<Object> mListData;
	private Type type_;

	public enum Type {
		Single_File, // A single filename
		Multiple_Files, // An EBList of files
		Single_String, Multiple_Strings
	};

	/**
	 * 
	 * @param data
	 *            if this is file data, then the absolute path to the file. If
	 *            it's a string, then the string
	 * @param dataType
	 *            can be either {@link Type#Single_File} or
	 *            {@link Type#Single_String}
	 */
	public DataSource(String data, Type dataType) {
		mData = data;
		type_ = dataType;
	}

	public DataSource(EBList<Object> listData, Type dataType) {
		mListData = listData;
		type_ = dataType;
	}

	public void setDataSource(String data, Type dataType) {
		mData = data;
		type_ = dataType;
	}

	public void setDataSource(EBList<Object> listData, Type dataType) {
		mListData = listData;
		type_ = dataType;
	}
	
	public Type getCurrentDataType() {
		return type_;
	}

	public String getCurrentData() {
		if (type_ == Type.Single_File || type_ == Type.Single_String)
			return mData;
		else if (type_ == Type.Multiple_Files)
			return ((File) mListData.get()).getAbsolutePath();
		else if (type_ == Type.Multiple_Strings)
			return ((String) mListData.get());
		else
			throw new IllegalStateException(
					"Unknown data type in the data source");
	}

	@Override
	public Section getSection() {
		JPanel poo = new JPanel();
		poo.add(new JLabel("Hi there!!!"));
		return new Section("Data Source", poo);
	}

	@Override
	public void receiveAction(Action e) {
		// TODO Auto-generated method stub

	}

}
