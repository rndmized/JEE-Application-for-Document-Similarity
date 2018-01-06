package ie.gmit.sw;

import java.io.InputStream;
import java.util.Collection;

public interface Parser<T> {

	public Collection<T> parse(InputStream f, String docID) throws Exception;

}
