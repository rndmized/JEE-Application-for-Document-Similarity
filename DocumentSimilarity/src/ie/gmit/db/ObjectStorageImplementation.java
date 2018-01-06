package ie.gmit.db;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;

import ie.gmit.sw.Document;
import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

public class ObjectStorageImplementation implements ObjectStorage {
	
	private ObjectContainer db = null;
	private EmbeddedConfiguration config;
	
	public ObjectStorageImplementation(String dbName) {
		
		config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport()); //Real lazy. Saves all the config commented out below
		config.common().add(new TransparentPersistenceSupport()); //Lazier still. Saves all the config commented out below
		config.common().updateDepth(7); //Propagate updates
		
		//Use the XTea lib for encryption. The basic Db4O container only has a Caesar cypher... Dicas quod non est ita!
		config.file().storage(new XTeaEncryptionStorage("password", XTEA.ITERATIONS64));
		
		//Open a local database. Use Db4o.openServer(config, server, port) for full client / server
		db = Db4oEmbedded.openFile(config, dbName + ".data");
	}
	

	/* (non-Javadoc)
	 * @see ie.gmit.sw.ObjectStorage#addDocument(ie.gmit.sw.Document)
	 */
	@Override
	public boolean addDocument(Document doc) {
		this.deleteDocument(doc);
		db.store(doc);
		db.commit();
		return true;
	}
	

	/* (non-Javadoc)
	 * @see ie.gmit.sw.ObjectStorage#loadDocumentList()
	 */
	@Override
	public List<Document> loadDocumentList() {
		List<Document> docs = new ArrayList<>();
		ObjectSet<Document> documents = db.query(Document.class);
		for (Document document : documents) {
			docs.add(document);
		}
		return docs;
	}
	

	/* (non-Javadoc)
	 * @see ie.gmit.sw.ObjectStorage#deleteDocument(ie.gmit.sw.Document)
	 */
	@Override
	public void deleteDocument(final Document d){
		ObjectSet<Document> result = db.query(new Predicate<Document>() {
			private static final long serialVersionUID = 777L;

			public boolean match(Document document) {
		        return document.getDocID().equals(d.getDocID());
		    }	
		});
		
		if (result.hasNext()) {
			//out.println("[getDocument] found " + d.getDocID());
			//out.println("Removing [" + d.getDocID() +"] from database.");
			db.delete(result.next());
			
		} else {
			//out.println("[Error] " + d.getDocID() + " is not in the database");
		}
		db.commit();
	}
	
	public void close(){
		db.close();
	}

}
