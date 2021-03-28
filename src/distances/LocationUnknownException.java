/**
 * 
 */
package distances;

/**
 * @author Wolfgang Bongartz
 * A certain location could not be found in the distance table.
 */
public class LocationUnknownException extends Exception {

	public LocationUnknownException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 9217338842470581395L;
}
