package ie.gmit.db;

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
/**
 * Implementation of ObjectStorage Interface.
 * Contains methods to access Object Database.
 * 
 * @author RnDMizeD
 * @version 1.0a
 */
public class ObjectStorageImplementation implements ObjectStorage {
	
	private ObjectContainer db = null;
	private EmbeddedConfiguration config;
	
	/**
	 * Takes String name and instantiates Object Storage for Object Database access for the named database.
	 * @param String dbName
	 */
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
	

	@Override
	public void addDocument(Document document) {
		// Look whether the document is already in the database, if so delete
		this.deleteDocument(document);
		// Add document to database;
		db.store(document);
		// Commit
		db.commit();
	}
	

	@Override
	public List<Document> loadDocumentList() {
		List<Document> docs = new ArrayList<>();
		//Query database for Document objects
		ObjectSet<Document> documents = db.query(Document.class);
		// Add documents to list
		for (Document document : documents) {
			docs.add(document);
		}
		// return list
		return docs;
	}
	

	@Override
	public void deleteDocument(final Document document){
		// Query database for documents with the same id
		ObjectSet<Document> result = db.query(new Predicate<Document>() {
			private static final long serialVersionUID = 777L;

			public boolean match(Document doc) {
		        return doc.getDocID().equals(document.getDocID());
		    }	
		});
		// If there is a match delete document.
		if (result.hasNext()) {
			db.delete(result.next());	
		}
		// Commit changes
		db.commit();
	}
	

}
