package ie.gmit.sw;

import java.io.InputStream;
import java.util.Collection;
/**
 * Parser Interface defining Parser behavior.
 * 
 * @author RnDMizeD
 * @version 1.1b
 * @param <T>
 */
public interface Parser<T> {
	
	/**
	 * Parses an Input Stream into a collection of Types T and returns such collection.
	 * 
	 * @param InputStream f
	 * @param String docID
	 * @return A collection of Types T
	 * @throws Exception
	 */
	public Collection<T> parse(InputStream f, String docID) throws Exception;

}
