package cluedo.control;
/**
 *  A simple class used to handle unexpected conditions 
 *   and errors during the runtime of the cluedo game.
 *   
 * @author Patrick Evans and Maria Legaspi
 *
 */
public class CluedoError extends RuntimeException {

	/**
	 * Creates the error with an error message.
	 * @param msg
	 */
	public CluedoError(String msg) {
		super(msg);
	}

}
