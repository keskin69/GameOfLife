package cell;

import java.util.HashMap;

public class Gene extends HashMap<String, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public long getValue(String key) {
		return get(key).longValue();
	}

	public void add(String key, long d) {
		put(key, new Long(d));
	}
}
