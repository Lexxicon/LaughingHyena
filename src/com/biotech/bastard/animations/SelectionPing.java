/**
 * 
 */
package com.biotech.bastard.animations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.Color;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class SelectionPing extends Animation {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SelectionPing.class);

	private int startDiameter, targetDiameter;
	private Color firstColor, secondColor;

	/**
	 * 
	 */
	public SelectionPing(
			PApplet parent,
			int length,
			int startDiameter,
			int targetDiameter,
			Color firstColor,
			Color secondColor) {
		super(parent);
		setLength(length);
		this.startDiameter = startDiameter;
		this.targetDiameter = targetDiameter;
		this.firstColor = firstColor;
		this.secondColor = secondColor;
	}

	/*
	 * @see com.biotech.bastard.animations.Animation#update()
	 */
	@Override
	public void update() {
		getParent().stroke(0, 0, 0, 0);
		float alpha = 255 - (PApplet.map(getCurrentFrame(), 0, getLength(), 175, 255));
		float adjustedDiameter = PApplet.map(getCurrentFrame(), 0, getLength(), startDiameter, targetDiameter);
		getParent().fill(
				firstColor.r,
				firstColor.g,
				firstColor.b,
				alpha);
		getParent().ellipse(0, 0, adjustedDiameter, adjustedDiameter);

		getParent().fill(
				secondColor.r,
				secondColor.g,
				secondColor.b,
				alpha + 1);
		getParent().ellipse(0, 0, adjustedDiameter, adjustedDiameter);
	}
}
