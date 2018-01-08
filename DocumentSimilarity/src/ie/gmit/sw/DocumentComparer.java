package ie.gmit.sw;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ie.gmit.db.AdditionRequest;
import ie.gmit.db.ObjectStorageAccessController;
import ie.gmit.db.ObjectStorageAccessRequest;
/**
 * Implements Runnable. Runs on a thread to compare a document to a set of
 * documents from the database by minhashing its contents and applying Jaccard
 * similarity to them. Takes Jobs from an input queue, processes them and puts
 * the similarity result on an output map where it can be fetched on completion.
 * 
 * @author RnDMizeD
 * @version 1.0b
 */
public class DocumentComparer implements Runnable {

	private BlockingQueue<Job> in;
	private Similarity similarity;
	private Map<String, Similarity> out;
	private MinHash minhash;
	private JaccardComputation jaccard;
	private ObjectStorageAccessController dbc;

	/**
	 * Constructor takes a BlockingQueue of incoming Jobs, a Map containing
	 * finalized Jobs, and a reference to the Database through a
	 * ObjectStorageAccessController.
	 * 
	 * @param BlockingQueye
	 *            of Jobs in
	 * @param Map<String,
	 *            Similarity> out
	 * @param ObjectStorageAccessController
	 *            dbc
	 */
	public DocumentComparer(BlockingQueue<Job> in, Map<String, Similarity> out, ObjectStorageAccessController dbc) {
		super();
		this.in = in;
		this.out = out;
		this.dbc = dbc;
		init();
	}

	/**
	 * Initializer
	 */
	private void init() {
		minhash = new MinHash();
		jaccard = new JaccardComputation();
	}

	@Override
	public void run() {
		try {
			// Take job from queue
			Job job = in.take();
			// Get similarity object
			similarity = job.getSimilarity();
			// get documents from db (if any)
			List<Document>  dbDocs = dbc.getDocumentList();
			// get list of minhashes for document
			List<Integer> minHashedDoc = minhash.getMinHashedDocument(similarity.getDocument());
			// for every document in the database
			for (Document document : dbDocs) {
				// if it is not the same document, compare
				if (document.getDocID() != similarity.getDocument().getDocID()) {
					try {
						// compute jaccard similarity index
						double result = jaccard.computeJaccard(minHashedDoc,
								minhash.getMinHashedDocument(document));
						// add similarity index to similarity object
						similarity.addSimilarity(document.getDocID(), result);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// Request document to be added to the database
			dbc.query(new AdditionRequest(similarity.getDocument()));
			// Set status of similarity to processed
			similarity.setProcessed();
			// put similarity in output queue with job task number as key in order to be fetched
			out.put(job.getTaskNumber(), similarity);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
