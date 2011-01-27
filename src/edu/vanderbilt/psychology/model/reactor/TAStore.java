package edu.vanderbilt.psychology.model.reactor;

/**
 * Keeps track of all the possible {@link Trigger}s and {@link Action}s
 * 
 * @author hamiltont
 * 
 */
public class TAStore {

	private static TAStore mInstance;
	

	public static TAStore getInstance() {
		if (mInstance == null)
			mInstance = new TAStore();
		return mInstance;
	}

	public void registerTrigger(Trigger t) {
		
	}
}
