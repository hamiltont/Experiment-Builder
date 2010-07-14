/**
 * TODO Using extensions (or extends) for standard Swing classes that I
 * don't need to modify the behavior of (such as extends JToolbar) 
 * results in class bloat and runtime lags. 
 * 
 * Class bloat slows down initial startup time. The time for a Java 
 * project to open is directly related to the number of class files. 
 * Runtime lags occur because of useless method calls. 
 * 
 * Creating a simple Toolbar class that has a static buildToolbar()
 * method which returns the JToolbar after configuration is a good
 * start, as it removes the runtime lag. Unfortunately, this does
 * not help the class bloat. 
 * 
 * The best solution I have right now is to consolidate all default
 * objects into one Builder class that simply has static methods to 
 * build many of the components required
 *  
 * @author Hamilton Turner
 */
package edu.vanderbilt.psychology.gui.main;