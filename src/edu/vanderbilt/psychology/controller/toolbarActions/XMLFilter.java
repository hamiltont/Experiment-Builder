package edu.vanderbilt.psychology.controller.toolbarActions;

import java.io.*;

/**
 * {@link XMLFilter} is used by {@link OpenExperimentAction} in order to filter the types of
 * files to be opened by EB to only .XML files, i.e. the type of file used
 * to contain an experiment.
 * 
 * @author sethfri
 *
 */

public class XMLFilter extends javax.swing.filechooser.FileFilter {
    
	public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
    }
    
    public String getDescription() {
        return ".xml files";
    }
}