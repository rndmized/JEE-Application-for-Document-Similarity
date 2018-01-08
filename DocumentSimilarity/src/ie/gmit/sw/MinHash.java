package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Operations to calculate MinHash
 * 
 * @author RnDMizeD
 * @version 1.0b
 */
public class MinHash {

	private final int MINHASH_NUMBER = 200;
	private int[] minhashes = new int[MINHASH_NUMBER];

	
	/**
	 * Constructor
	 */
	public MinHash() {
		init();
	}
	
	/**
	 * Initializer
	 */
	private void init() {
		for (int i = 0; i < MINHASH_NUMBER; i++) {
			minhashes[i] = new Random().nextInt();
		}
	}
	
	/**
	 * Returns a List of Integers minhashed, result of the hashcode of a Document Shingles  XORed 
	 * with random numbers to take the MINHASH_NUMBER amount of the lowest Integers of such document.
	 * 
	 * @param Document doc
	 * @return List of Integers
	 * @throws InterruptedException
	 */
	public List<Integer> getMinHashedDocument(Document doc) throws InterruptedException {
		Document document = doc;
		BlockingQueue<Shingle> queue = new LinkedBlockingQueue<>(document.getShingles());
		List<Integer> minHashList = new ArrayList<>();
		for (Integer hash : minhashes) {
			int min = Integer.MAX_VALUE;
			for (Shingle shingle : queue) {
				int minHash = shingle.getHashcode() ^ hash; 
				if(minHash < min) min = minHash;
			}
			minHashList.add(min);
		}
		return minHashList;
	}

}
