/**
 * 
 */
package com.biotech.bastard;

import processing.core.PApplet;

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

	public static Color[] createGradiant(Color c1, Color c2, int steps) {
		Color[] gradiant = new Color[steps];
		for (int i = 0; i < steps; i++) {
			int r = (int) PApplet.map(i, 0, steps, c1.r, c2.r);
			int g = (int) PApplet.map(i, 0, steps, c1.g, c2.g);
			int b = (int) PApplet.map(i, 0, steps, c1.b, c2.b);
			float a = PApplet.map(i, 0, steps, c1.a, c2.a);

			gradiant[i] = new Color(r, g, b, a);
		}

		return gradiant;
	}
}
