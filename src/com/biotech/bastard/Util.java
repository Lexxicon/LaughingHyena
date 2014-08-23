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
public class Util {
	public static float clamp(float in, float r1, float r2) {
		if (r1 < r2) {
			return Math.max(Math.min(in, r2), r1);
		} else {
			return Math.max(Math.min(in, r1), r2);
		}
	}

	public static float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static void main(String[] args) {
		System.out.println(clamp(2, 1, -1));
	}
}
