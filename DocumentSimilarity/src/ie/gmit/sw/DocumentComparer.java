package ie.gmit.sw;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ie.gmit.db.AdditionRequest;
import ie.gmit.db.ObjectStorageAccessController;
import ie.gmit.db.ObjectStorageAccessRequest;

public class DocumentComparer implements Runnable {

	private BlockingQueue<Job> in;
	private Similarity similarity;
	private Map<String, Similarity> out;
	private MinHash minhash;
	private JaccardComputation jaccard;
	private ObjectStorageAccessController dbc;

	public DocumentComparer(BlockingQueue<Job> in, Map<String, Similarity> out, ObjectStorageAccessController dbc) {
		super();
		this.in = in;
		this.out = out;
		this.dbc = dbc;
		init();
	}

	private void init() {
		minhash = new MinHash();
		jaccard = new JaccardComputation();
	}

	@Override
	public void run() {
		try {
			Job job = in.take();
			similarity = job.getSimilarity();
			List<Document>  dbDocs = dbc.getDocumentList();
			//System.out.println(dbDocs.size());
			List<Integer> minHashedDoc = minhash.getMinHashedDocument(similarity.getDocument());
			for (Document document : dbDocs) {
				if (document.getDocID() != similarity.getDocument().getDocID()) {
					try {
						double result = jaccard.computeJaccard(minHashedDoc,
								minhash.getMinHashedDocument(document));
						similarity.addSimilarity(document.getDocID(), result);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			dbc.query(new AdditionRequest(similarity.getDocument()));
			similarity.setProcessed();
			out.put(job.getTaskNumber(), similarity);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
