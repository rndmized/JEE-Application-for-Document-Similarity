package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
		init();

	}

	private void init() {

		pool = Executors.newFixedThreadPool(50);

		for (int i = 0; i < minhashes.length; i++) {
			minhashes[i] = new Random().nextInt();
		}

		List<Integer> list = new ArrayList<>(minhashes.length);
		for (int i = 0; i < list.size(); i++) {
			list.set(i, Integer.MAX_VALUE);

		}
		map.put(docID, list);

	}

	public void run() {
		Shingle next;
		try {
			next = queue.take();
			pool.execute(new Runnable() {
				public void run() {
					for (int i = 0; i < minhashes.length; i++) {
						int value = next.getHashcode() ^ minhashes[i]; // XOR THE VALUE WITH MIN HASH
						List<Integer> hashes = map.get(next.getDocId());
						if (value < hashes.get(i)) {
							hashes.set(i, value);
						}
					}
				}

			});
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // Blocking queue - throws exception

		// double result = computeJaccard();
	}
}
