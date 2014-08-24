/**
 * 
 */
package com.biotech.bastard.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public abstract class ScreenHotspot {

	@SuppressWarnings("unused")
	private static transient final Logger LOGGER = LoggerFactory.getLogger(ScreenHotspot.class);

	protected static enum State {
		ACTIVE, DISABLED, OVER, DOWN;
	}

	private boolean active;
	private boolean wasOver;
	private State state;

	private final Collection<ScreenHotspot> owner;

	/**
	 * 
	 */
	public ScreenHotspot(Collection<ScreenHotspot> owner) {
		this.owner = owner;
		owner.add(this);
	}

	final public void handle(float x, float y, boolean m1, boolean m2) {
		boolean isOver = isWithin(x, y);
		if (active) {
			if (isOver != wasOver) {
				if (isOver) {
					state = State.OVER;
					onEnter();
				} else {
					state = State.ACTIVE;
					onExit();
				}
			} else {
				if (isOver) {
					if (m1) {
						state = State.DOWN;
						onClickLeft();
					} else if (m2) {
						state = State.DOWN;
						onClickRight();
					} else {
						state = State.OVER;
						onHover();
					}
				} else {
					state = State.ACTIVE;
				}
			}
		} else {
			state = State.DISABLED;
		}
		wasOver = isOver;
	}

	public abstract boolean isWithin(float x, float y);

	public abstract void draw(PApplet parrent);

	public void onHover() {
	}

	public void onClickRight() {
	}

	public void onClickLeft() {
	}

	public void onEnter() {
	}

	public void onExit() {
	}

	public void remove() {
		owner.remove(this);
		disable();
	}

	final public void enable() {
		setActive(true);
	}

	final public void disable() {
		setActive(false);
	}

	final public boolean isActive() {
		return active;
	}

	final public void setActive(boolean active) {
		this.active = active;
	}

	public State getState() {
		return state;
	}
}
