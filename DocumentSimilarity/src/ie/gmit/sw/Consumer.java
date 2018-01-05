package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
	private int MINHASH_NUMBER = 200;

	private BlockingQueue<Shingle> queue;
	private ExecutorService pool;
	private int[] minhashes = new int[MINHASH_NUMBER];
	private Map<String, List<Integer>> map = new ConcurrentHashMap<>();
	private String docID;

	public Consumer(String docID, BlockingQueue<Shingle> queue) {
		this.queue = queue;
		this.docID = docID;
		this.init();

	}

	private void init() {

		pool = Executors.newFixedThreadPool(50);

		for (int i = 0; i < MINHASH_NUMBER; i++) {
			minhashes[i] = new Random().nextInt();
		}

		List<Integer> list = new ArrayList<>(MINHASH_NUMBER);
		for (int i = 0; i < MINHASH_NUMBER; i++) {
			list.add(i, Integer.MAX_VALUE);

		}
		map.put(docID, list);
	}

	public void run() {
		Shingle next;
		try {
			next = queue.take();
			pool.execute(new Runnable() {
				public void run() {
					for (int i = 0; i < MINHASH_NUMBER; i++) {
						int value = next.getHashcode() ^ minhashes[i]; // XOR THE VALUE WITH MIN HASH
						List<Integer> hashes = map.get(next.getDocId());
						if (value < hashes.get(i)) {
							hashes.set(i, value);
						}
					}
				}
			});
			Document doc = new Document(docID);
			doc.setMinHashes(map.get(docID));
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // Blocking queue - throws exception
	}
	
	private double computeJaccard(final List<Integer> m1, final List<Integer> m2) {
		if (m1.equals(m2)) {
            return 1.0;
		}
		
		Set<Integer> union = new HashSet<Integer>();
		union.addAll(m1);
		union.addAll(m2);
		
		int intersection = m1.size() + m2.size()
				- union.size();
		
		 return 1.0 * intersection / union.size();
	}

}
