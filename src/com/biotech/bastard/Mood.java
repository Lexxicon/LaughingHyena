/**
 * 
 */
package com.biotech.bastard;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public enum Mood {
	HAPPY("Happy"), ANGRY("Angry"), HOMICIDAL("Homicidal"), NEUTRAL("Neutral");

	private final String displayName;

	Mood(String displayName) {
		this.displayName = displayName;
	}

	/*
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return displayName;
	}
}
