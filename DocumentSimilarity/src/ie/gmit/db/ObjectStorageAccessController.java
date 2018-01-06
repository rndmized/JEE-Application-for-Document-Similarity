package ie.gmit.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.AdditionRequest;
import ie.gmit.sw.Document;



public class ObjectStorageAccessController implements Runnable {
	
	private BlockingQueue<ObjectStorageAccessRequest> queue;
	private ObjectStorage db;
	private volatile boolean keepRunning;
	private volatile List<Document> dbDocs;

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
				req = queue.take();
				if (req instanceof PoisonRequest) {
					keepRunning = false;
				} else if (req instanceof AdditionRequest){
					db.addDocument(((AdditionRequest) req).getDocument());
				} else {
					dbDocs = db.loadDocumentList();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void query(ObjectStorageAccessRequest request) {
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public List<Document> getDocumentList(){
		return this.dbDocs;
	}

}
