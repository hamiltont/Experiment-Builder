/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

/**
 * Interface for any sort of Events that an {@link Property} would like to fire
 * 
 * @author Hamilton Turner
 *
 */
public interface PropertyEvent {
	
	public String getName();

	/**
	 * @return A short (typically <10 characters) string that describes any
	 *         value info
	 */
	public String getValue();

	/**
	 * This method may not survive a few more revisions. Right now it it
	 * intended as a catch-all, or a way for an event to include any information
	 * that is too large or unmanageable to properly go into the getValue()
	 * method.
	 * 
	 * @return A big string containing any info that might be interesting
	 */
	public String getStateInfo();
}
