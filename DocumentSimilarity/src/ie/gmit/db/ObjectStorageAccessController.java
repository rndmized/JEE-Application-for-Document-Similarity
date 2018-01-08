package ie.gmit.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.Document;


/**
 * Runnable class using a BlockingQueue to control access Object Database.
 * Maintains a cached list of Document objects from the database.
 * 
 * @author RnDMizeD
 * @version 1.0b
 */
public class ObjectStorageAccessController implements Runnable {
	
	private BlockingQueue<ObjectStorageAccessRequest> queue;
	private ObjectStorage db;
	private volatile boolean keepRunning;
	private volatile List<Document> dbDocs;

	/**
	 * Instantiate ObjectStorageAccessController
	 * 
	 * @param queue
	 */
	public ObjectStorageAccessController(BlockingQueue<ObjectStorageAccessRequest> queue) {
		this.queue = queue;
		keepRunning = true;
		db = new ObjectStorageImplementation("documents");
		dbDocs = new ArrayList<>();
	}
	
	@Override
	public void run() {
		while (keepRunning) {
			ObjectStorageAccessRequest req;
			try {
				// Take request from queue
				req = queue.take();
				//Depending on the request type
				if (req instanceof PoisonRequest) {
					//finish loop
					keepRunning = false;
				} else if (req instanceof AdditionRequest){
					//add document to db
					db.addDocument(((AdditionRequest) req).getDocument());
					// update instance of list
					dbDocs = db.loadDocumentList();
				} else {
					// update instance of list
					dbDocs = db.loadDocumentList();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Add request to queue.
	 * 
	 * @param ObjectStorageAccessRequest request
	 */
	public void query(ObjectStorageAccessRequest request) {
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns List of documents in the database
	 * 
	 * @return List<Document>
	 */
	public List<Document> getDocumentList(){
		return this.dbDocs;
	}

}
