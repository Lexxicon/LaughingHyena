/**
 * 
 */
package com.biotech.bastard.cards;

import java.awt.Rectangle;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public abstract class Card {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Card.class);

	private static HashMap<Class<? extends Card>, PImage> imageMap = new HashMap<>();

	final PApplet owner;

	private int x, y;

	public static Card selected;

	/**
	 * 
	 */
	public Card(PApplet owner) {
		this.owner = owner;
	}

	protected abstract String getImagePath();

	protected PImage getImage() {
		if (!imageMap.containsKey(getClass())) {
			if (getImagePath() != null) {
				imageMap.put(getClass(), owner.loadImage(getImagePath()));
			}
		}

		return imageMap.get(getClass());
	}

	public abstract Action getAction();

	public abstract String getName();

	public Rectangle getFrame() {
		if (getImage() != null) {
			return new Rectangle(getX(), getY(), getImage().width, getImage().height);
		} else {
			return new Rectangle(70, 110);
		}
	}

	public void draw() {
		owner.image(getImage(), getX(), getY());
		if (selected != null && selected != this) {
			owner.fill(0, 0, 0, 128);
			Rectangle rect = getFrame();
			owner.rect(rect.x, rect.y, rect.width, rect.height);
		}
	}

	/**
	 * @return
	 */
	public boolean needsTarget() {
		return getAction().needsTarget();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
