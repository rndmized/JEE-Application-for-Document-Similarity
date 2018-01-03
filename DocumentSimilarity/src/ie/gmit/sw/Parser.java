package ie.gmit.sw;

import java.io.InputStream;

public interface Parser {
	
	public Object parse(InputStream file) throws Exception;

}
