package cluedo.control;
/**
 *  A simple class used to handle unexpected conditions 
 *   and errors during the runtime of the cluedo game.
 *   
 * @author Patrick
 *
 */
public class CluedoError extends RuntimeException {

	public CluedoError(String msg) {
		super(msg);
	}

}
