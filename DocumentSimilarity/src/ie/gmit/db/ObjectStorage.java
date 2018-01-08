package ie.gmit.db;

import java.util.List;

import ie.gmit.sw.Document;
/**
 * ObjectStorage Interface Definition
 * 
 * @author RnDMizeD
 * @version 1.0a
 */
public interface ObjectStorage {
	/**
	 * Adds Document to Object Database.
	 * 
	 * @param Document document
	 */
	public void addDocument(Document document);

	/**
	 * Returns list of documents from Object Database.
	 * 
	 * 
	 * @return <code>List<Document><code>
	 */
	public List<Document> loadDocumentList();
	
	/**
	 * Search and remove Document d from Object Database.
	 * 
	 * @param Document document
	 */
	public void deleteDocument(Document document);

}