package ie.gmit.sw;

import java.util.List;

public class Document {
	
	private String docID;
	private List<Integer> minHashes;
	
	
	public Document(String docID) {
		super();
		this.docID = docID;
	}


	public String getDocID() {
		return docID;
	}


	public void setDocID(String docID) {
		this.docID = docID;
	}


	public List<Integer> getMinHashes() {
		return minHashes;
	}


	public void setMinHashes(List<Integer> minHashes) {
		this.minHashes = minHashes;
	}
	
}
