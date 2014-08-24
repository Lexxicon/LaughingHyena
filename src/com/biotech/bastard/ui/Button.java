/**
 * 
 */
package com.biotech.bastard.ui;

import java.awt.Rectangle;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;
import processing.core.PConstants;

import com.biotech.bastard.Color;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class Button extends ScreenHotspot {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Button.class);

	Rectangle rect;
	String text;
	ButtonColorPalette palette;

	/**
	 * @param owner
	 */
	public Button(Collection<ScreenHotspot> owner, Rectangle rect, String text, ButtonColorPalette palette) {
		super(owner);
		this.rect = rect;
		this.text = text;
		this.palette = palette;
	}

	/*
	 * @see com.biotech.bastard.ui.ScreenHotspot#draw(processing.core.PApplet)
	 */
	@Override
	public void draw(PApplet parent) {
		parent.pushMatrix();
		parent.pushStyle();
		setFill(parent);
		parent.translate(rect.x, rect.y);

		parent.rect(0, 0, rect.width, rect.height);
		parent.fill(palette.text.r, palette.text.g, palette.text.b, palette.text.a);

		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.text(text, rect.width / 2, rect.height / 2);
		parent.popStyle();
		parent.popMatrix();
	}

	private void setFill(PApplet parent) {
		Color target = null;
		switch (getState()) {
		case ACTIVE:
			target = palette.active;
			break;
		case DISABLED:
			target = palette.disabled;
			break;
		case DOWN:
			target = palette.down;
			break;
		case OVER:
			target = palette.over;
			break;
		default:
			LOGGER.warn("Unknown state {}", getState());
			target = palette.active;
			break;
		}
		parent.fill(target.r, target.g, target.b, target.a);
	}

	/*
	 * @see com.biotech.bastard.ui.ScreenHotspot#isWithin(float, float)
	 */
	@Override
	public boolean isWithin(float x, float y) {
		return rect.contains(x, y);
	}

}
