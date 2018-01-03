package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ShingleParser implements Parser {

	private Deque<String> buffer = new LinkedList<>();
	private BlockingQueue<Shingle> bq = new LinkedBlockingQueue<>();
	private int SHINGLE_SIZE;
	private String docID;

	public ShingleParser(int SHINGLE_SIZE, String docID) {
		super();
		this.SHINGLE_SIZE = SHINGLE_SIZE;
		this.docID = docID;
	}
	
	@Override
	public Object parse(InputStream file) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			String[] words = line.split("[\\s@&.,;:?$+-]+");// use regex
			//System.out.println(words.length);
			for (int i = 0; i < SHINGLE_SIZE; i++) {
				if ((words.length - i) > 0) {
					buffer.add(words[i]);
				}
			}
			Shingle s = getNextShingle(docID);
			try {
				bq.put(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return bq;

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
