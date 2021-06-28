package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.PurpleBadge;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

public class getPurpleBadgeResponse extends AbstractResponse {

	private PurpleBadge pb;

	public getPurpleBadgeResponse(PurpleBadge pb) {
		super(ResponseStatus.Acknowledged);
		// TODO Auto-generated constructor stub
		this.setPb(pb);
	}

	/**
	 * @return the pb
	 */
	public PurpleBadge getPb() {
		return pb;
	}

	/**
	 * @param pb the pb to set
	 */
	public void setPb(PurpleBadge pb) {
		this.pb = pb;
	}

}
