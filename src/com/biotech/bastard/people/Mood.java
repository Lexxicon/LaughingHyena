/**
 * 
 */
package com.biotech.bastard.people;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public enum Mood {
	HAPPY("Happy", 1), ANGRY("Angry", 1), HOMICIDAL("Homicidal", .1), NEUTRAL("Neutral", 1), DEAD("Dead", 0);

	public static Mood weightedRandom() {

		double totalWeight = 0.0d;
		for (Mood i : values())
		{
			totalWeight += i.weight;
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < values().length; ++i)
		{
			random -= values()[i].weight;
			if (random <= 0.0d)
			{
				randomIndex = i;
				break;
			}
		}
		return values()[randomIndex];
	}

	private final String displayName;
	private final double weight;

	Mood(String displayName, double weight) {
		this.displayName = displayName;
		this.weight = weight;
	}

	/*
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return displayName;
	}
}
