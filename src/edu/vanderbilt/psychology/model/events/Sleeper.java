package edu.vanderbilt.psychology.model.events;

public class Sleeper implements EventListener {

	public static final int ACTION_PAUSE_EXPERIMENT = 0;

	@Override
	public void receiveEvent(Event e) {
		long pauseTime = 2000;

		if (e.getData() != null && e.getData() instanceof Long)
			pauseTime = (Long) e.getData();
		
		synchronized (Thread.currentThread()) {
			try {
				Thread.currentThread().wait(pauseTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
