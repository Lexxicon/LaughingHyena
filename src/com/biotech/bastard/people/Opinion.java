/**
 * 
 */
package com.biotech.bastard.people;

import com.biotech.bastard.Util;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class Opinion {
	/**
	 * How much someone likes or dislikes a person range: 1.0, -1.0 inclusive
	 */
	private float approval = 0.0f;
	/**
	 * the degree to which someone knows the person, lower means better
	 * understanding. range 1, Max Value inclusive. Max value indicates no
	 * knowledge.
	 */
	private int awareness = Integer.MAX_VALUE;

	public Opinion() {

	}

	public boolean isTouchable() {
		return awareness < 4;
	}

	public Opinion(float approval, int awareness) {
		setApproval(approval);
		setAwareness(awareness);
	}

	/**
	 * Adjusts this opinion based on the opinion of the source and the sources
	 * opinion of the target
	 * 
	 * @param source
	 *            opinion of the source of this change
	 * @param target
	 *            the opinion of the source on the target
	 */
	public void influence(Opinion source, Opinion target) {
		// if we know of the source
		if (source.awareness < Integer.MAX_VALUE) {
			// if the source has better awareness than we do of the target
			awareness = Math.min(awareness, target.awareness + 1);

			// downscale the approval amount based upon how aware the source is
			// of the target
			float scaledApproval = (target.approval / target.awareness);

			// adjust the scaled approval by how we feel about the source
			scaledApproval = source.approval * scaledApproval;
			setApproval(approval + scaledApproval);
		}
	}

	public float getApproval() {
		return approval;
	}

	public void setApproval(float approval) {
		this.approval = Util.clamp(approval, -1, 1);
		;
	}

	public int getAwareness() {
		return awareness;
	}

	public void setAwareness(int awareness) {
		this.awareness = awareness;
	}

	public Relation getRelation() {
		return Relation.fromOpinion(this);
	}
}
