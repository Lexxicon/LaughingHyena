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
}
