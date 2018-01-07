package ie.gmit.sw;

import ie.gmit.db.ObjectStorageAccessRequest;

public class AdditionRequest extends ObjectStorageAccessRequest {
	 
	private Document document;

	public AdditionRequest(Document document) {
		super();
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	
	 

}
