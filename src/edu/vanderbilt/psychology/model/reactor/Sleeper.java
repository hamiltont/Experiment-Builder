package edu.vanderbilt.psychology.model.reactor;

public class Sleeper implements ActionListener {

	public static final int ACTION_PAUSE_EXPERIMENT = 0;

	@Override
	public void receiveAction(Action e) {
		long pauseTime = 2000;

		if (e.getData() != null && e.getData() instanceof Long)
			pauseTime = (Long) e.getData();
		
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
	}
}
