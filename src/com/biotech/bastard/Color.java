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
public class Color {
	public final int r, g, b;
	public final float a;

	/**
	 * 
	 */
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	public Color(int r, int g, int b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
}
