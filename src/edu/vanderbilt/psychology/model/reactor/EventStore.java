package edu.vanderbilt.psychology.model.reactor;

public class EventStore {

	private static EventStore mInstance;
	
	private static EventStore getInstance() {
		if (mInstance == null)
			mInstance = new EventStore();
		return mInstance;
	}
	
	private void registerTrigger() {
		
	}
}
