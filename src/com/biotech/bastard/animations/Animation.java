/**
 * 
 */
package com.biotech.bastard.animations;

import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public abstract class Animation {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Animation.class);

	private boolean isDone = true;
	private boolean pingpong;
	private boolean loop;

	// used to pingpong
	private boolean goingUp = true;
	private int length;
	private int currentFrame;

	final private PApplet parent;

	private Point location;

	public Animation(PApplet parent) {
		this.parent = parent;
	}

	public final void draw() {
		if (!isDone) {
			getParent().pushMatrix();

			getParent().translate(getLocation().x, getLocation().y);
			update();

			if (isPingpong()) {
				if (goingUp) {
					goingUp = ++currentFrame < length - 1;
				} else {
					goingUp = --currentFrame <= 0;
				}
			} else {
				currentFrame = ++currentFrame % length;
			}

			if (!isLoop()) {
				if (currentFrame <= 0 || currentFrame >= length - 1) {
					setDone(true);

					LOGGER.debug("Finished {} animation", getClass().getSimpleName());
				}
			}

			getParent().popMatrix();
		}
	}

	protected abstract void update();

	/**
	 * @return the isDone
	 */
	public boolean isDone() {
		return isDone;
	}

	/**
	 * @param isDone
	 *            the isDone to set
	 */
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public void restart() {
		setDone(false);
		setCurrentFrame(0);
	}

	/**
	 * @return the pingpong
	 */
	public boolean isPingpong() {
		return pingpong;
	}

	/**
	 * @param pingpong
	 *            the pingpong to set
	 */
	public void setPingpong(boolean pingpong) {
		this.pingpong = pingpong;
	}

	/**
	 * @return the loop
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * @param loop
	 *            the loop to set
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the parent
	 */
	public PApplet getParent() {
		return parent;
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

}
