package ie.gmit.db;

import ie.gmit.sw.Document;

/**
 * Request to add document to ObjectStorage.
 * 
 * @author RnDMizeD
 * @version 1.0a
 */
public class AdditionRequest extends ObjectStorageAccessRequest {
	 
	private Document document;
	/**
	 * Document to store in the ObjectStorage.
	 * @param document
	 */
	public AdditionRequest(Document document) {
		super();
		this.document = document;
	}

	/**
	 * Returns Document.
	 * @return a Document
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * Set Document to param document.
	 * @param document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	
	
	 

}
