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
public enum Item {
	WEAPON("Weapons"), DRUG("Drugs"), GIFT("Gifts");

	private final String displayName;

	Item(String displayName) {
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
