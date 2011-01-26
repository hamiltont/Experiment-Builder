package edu.vanderbilt.psychology.model.reactor;

public class TAStore {

	private static TAStore mInstance;
	
	private static TAStore getInstance() {
		if (mInstance == null)
			mInstance = new TAStore();
		return mInstance;
	}
	
	private void registerTrigger() {
		
	}
}
