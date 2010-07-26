package edu.vanderbilt.psychology;

public class Tuple {
	int[] values;

	public Tuple(int[] ints) {
		if (ints.length != 3)
			throw new IllegalArgumentException();

		values = ints;
	}
	
	public int getFirst() {
		return values[0];
	}
	
	public int getSecond() {
		return values[1];
	}
	
	public int getThird() {
		return values[2];
	}

	public String toString() {
		StringBuffer s = new StringBuffer("[");
		s.append(values[0]);
		s.append(",");
		s.append(values[1]);
		s.append(",");
		s.append(values[2]);
		s.append("]");
		return s.toString();
	}
}
