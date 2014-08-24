/**
 * 
 */
package com.biotech.bastard.animations;

import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class InformationFlow extends Animation {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(InformationFlow.class);

	Point source, target;
	int speed;

	/**
	 * @param parent
	 */
	public InformationFlow(PApplet parent, Point source, Point target, int speed) {
		super(parent);

		this.source = source;
		this.target = target;
		this.speed = speed;
		setLength(speed);
	}

	/*
	 * @see com.biotech.bastard.animations.Animation#update()
	 */
	@Override
	protected void update() {
		float x = PApplet.map(getCurrentFrame(), 0, getLength(), source.x, target.x);
		float y = PApplet.map(getCurrentFrame(), 0, getLength(), source.y, target.y);

		PApplet p = getParent();

		p.pushMatrix();

		p.translate(x, y);

		p.rect(0, 0, 100, 100);

		p.popMatrix();

	}
}
