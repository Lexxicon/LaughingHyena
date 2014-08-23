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
public enum Relation {
	STRANGER("Stranger"),
	LIKES("Likes"),
	DISLIKES("Dislikes"),
	AQUAINTENCE("Aquaintence"),
	HATE("Hate"),
	ADDORE("Adore"),
	INDIFFERENT("Indifferent");

	public static Relation fromOpinion(Opinion opinion) {
		if (opinion.getAwareness() == Integer.MAX_VALUE) {
			return STRANGER;
		}
		if (opinion.getAwareness() > 3) {
			return AQUAINTENCE;
		}
		if (opinion.getApproval() > 0.1) {
			if (opinion.getApproval() > .75) {
				return ADDORE;
			}
			return LIKES;
		}

		if (opinion.getApproval() < 0.1) {
			if (opinion.getApproval() < -.75) {
				return HATE;
			}
			return DISLIKES;
		}

		return INDIFFERENT;
	}

	private final String displayString;

	/**
	 * 
	 */
	private Relation(String displayString) {
		this.displayString = displayString;
	}

	/*
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return displayString;
	}
}
