package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
/**
 * Implementation of Parser to parse text documents into Shingles.
 * 
 * @author RnDMizeD
 *
 */
public class ShingleParser implements Parser<Shingle> {

	private int SHINGLE_SIZE;
	
	private Deque<String> buffer = new LinkedList<>();
	private Set<Shingle> s1 = new TreeSet<>();

	/**
	 * 
	 * @param SHINGLE_SIZE
	 */
	public ShingleParser(int SHINGLE_SIZE) {
		super();
		this.SHINGLE_SIZE = SHINGLE_SIZE;
	}
	

	@Override
	public Collection<Shingle> parse(InputStream part, String docID) throws Exception {
		// Create a buffer reader
		BufferedReader br = new BufferedReader(new InputStreamReader(part));
		//Instantiate set into a tree set and String line
		s1 = new TreeSet<>();
		String line = null;
		// for every line in the InputStream
		while ((line = br.readLine()) != null) {
			// Split it eliminating symbols and spaces 
			String[] words = line.split("[\\s@&.,;:?$+-]+");// using regex
			//Add words up to shingle size to buffer list
			for (int i = 0; i < SHINGLE_SIZE; i++) {
				if ((words.length - i) > 0) {
					buffer.add(words[i]);
				}
			}
			//Get shingle
			Shingle s = getNextShingle(docID);
			//Add shingle to set
			s1.add(s);
		}
		return s1;
	}

	/**
	 * Takes words from a buffer and creates an instance of a Shingle with a document ID and a hashcode.
	 * 
	 * @param String docID
	 * @return a Shingle
	 */
	private Shingle getNextShingle(String docID) {
		int counter = 0;
		StringBuilder sb = new StringBuilder();
		while (counter < SHINGLE_SIZE) {
			if (buffer.peek() != null) {
				sb.append(buffer.poll());
			}
			counter++;
		}
		return new Shingle(docID, sb.toString().hashCode());
	}
}
