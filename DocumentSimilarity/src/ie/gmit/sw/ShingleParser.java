package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class ShingleParser implements Parser<Shingle> {

	private int SHINGLE_SIZE;
	
	private Deque<String> buffer = new LinkedList<>();
	
	//**********************  TESTING   ********************************//
	private Set<Shingle> s1 = new TreeSet<>();
	//******************************************************************//

	public ShingleParser(int SHINGLE_SIZE) {
		super();
		this.SHINGLE_SIZE = SHINGLE_SIZE;
	}
	
	@Override
	public Collection<Shingle> parse(InputStream part, String docID) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(part));
		s1 = new TreeSet<>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] words = line.split("[\\s@&.,;:?$+-]+");// use regex
			for (int i = 0; i < SHINGLE_SIZE; i++) {
				if ((words.length - i) > 0) {
					buffer.add(words[i]);
				}
			}
			Shingle s = getNextShingle(docID);
			s1.add(s);
		}
		System.out.println(s1.size());
		return s1;
	}

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
