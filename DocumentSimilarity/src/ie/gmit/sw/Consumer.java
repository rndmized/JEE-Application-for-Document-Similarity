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
	private  int MINHASH_NUMBER = 200;
	private BlockingQueue<Shingle> queue;
	private ExecutorService pool;
	private int[] minhashes = new int[MINHASH_NUMBER];

	
	public Consumer(BlockingQueue<Shingle> queue){
		this.queue = queue;
		init();

	}

	private void init(){
		
		pool = Executors.newFixedThreadPool(50);

		for(int i = 0; i< minhashes.length; i++){
			minhashes[i] =  new Random().nextInt();
		}
		
	}

	public void run(){
		Shingle next = queue.take(); //Blocking queue - throws exception
		pool.execute(new Runnable(){
			public void run(){
				for(int i = 0; i < minhashes.length; i++){
					int value = next.getHashcode() ^ minhashes[i]; // XOR THE VALUE WITH MIN HASH
					List<Integer> hashes = map.get(next.getDocId());
					if (value < hashes.get(i)){
						hashes.set(i, value);
					}
				}
			}

		});
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		double result = computeJaccard();
	}
}
