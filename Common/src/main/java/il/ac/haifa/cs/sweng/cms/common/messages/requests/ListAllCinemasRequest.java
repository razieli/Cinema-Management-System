package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class ListAllCinemasRequest extends AbstractRequest {
    private Object obj;
	public ListAllCinemasRequest(Object obj) { this.setObj(obj);}
	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}
	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
