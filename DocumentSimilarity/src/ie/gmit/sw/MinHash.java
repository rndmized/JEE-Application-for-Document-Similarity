package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MinHash {

	private final int MINHASH_NUMBER = 200;
	private int[] minhashes = new int[MINHASH_NUMBER];
	private Map<String, List<Integer>> map = new ConcurrentHashMap<>();

	public MinHash() {
		init();
	}

	private void init() {
		for (int i = 0; i < MINHASH_NUMBER; i++) {
			minhashes[i] = new Random().nextInt();
		}
	}

	public List<Integer> getMaxIntList() {

		List<Integer> list = new ArrayList<>(MINHASH_NUMBER);
		for (int i = 0; i < MINHASH_NUMBER; i++) {
			list.add(i, Integer.MAX_VALUE);

		}
		return list;
	}

	public List<Integer> getMinHashedDocument(Document doc) throws InterruptedException {
		Document document = doc;
		BlockingQueue<Shingle> queue = new LinkedBlockingQueue<>(document.getShingles());
		map.put(document.getDocID(), getMaxIntList());
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
