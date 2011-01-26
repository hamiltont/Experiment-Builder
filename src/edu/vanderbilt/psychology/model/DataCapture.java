/**
 * 
 */
package edu.vanderbilt.psychology.model;

import edu.vanderbilt.psychology.model.inputs.Input;
import edu.vanderbilt.psychology.model.properties.Property;
import edu.vanderbilt.psychology.model.reactor.Event;
import edu.vanderbilt.psychology.model.reactor.EventListener;

/**
 * <p>
 * {@link DataCapture} can record/log information about events that are
 * happening in the software (such as a picture showing up on screen), or events
 * that users initiate (such as a key press). {@link Property}s and
 * {@link Input}s will alert {@link DataCapture} about events that occur.
 * </p>
 * 
 * <p>
 * {@link DataCapture} will be notified of quite a large number of events.
 * Currently it will log information about all of them, but in the future there
 * will be some filtering method to throw away data about un-interesting events.
 * </p>
 * 
 * <p>
 * A quick sample of a single event record:
 * </p>
 * 
 * <table border=1>
 * <tr>
 * <td>Slide Number<sup>1</sup></td>
 * <td>Sender Type<sup>2</sup></td>
 * <td>Sender Name<sup>3</sup></td>
 * <td>Event Name</td>
 * <td>Event Value<sup>4</sup></td>
 * <td>Event State Info<sup>5</sup></td>
 * </tr>
 * <tr>
 * <td>12</td>
 * <td>Property</td>
 * <td>Movement</td>
 * <td>Started</td>
 * <td>59</td>
 * <td></td>
 * </tr>
 * </table>
 * <sup>1</sup>Or Slide Name<br>
 * <sup>2</sup>Valid types at time of writing are {@link Property} or
 * {@link Input}<br>
 * <sup>3</sup>This could come from a class in
 * {@link edu.vanderbilt.psychology.model.properties} or from a loaded
 * plugin</br> <sup>4</sup>The units here are not unified, each event can use
 * it's own units<br>
 * <sup>5</sup>Not sure we should keep this field, but essentially this would be
 * a "catch all" field for anything not covered by the other fields<br>
 * 
 * <br>
 * <br>
 * 
 * @author Hamilton Turner
 * 
 */
public class DataCapture implements EventListener {

	public String getName() {
		return "DataCapture";
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
}
