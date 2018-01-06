package ie.gmit.sw;

import java.util.TreeSet;

public class Document {
	
	private String docID;
	private TreeSet<Shingle> shingles;
	
	public Document() {
		super();
	}
	
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

	public TreeSet<Shingle> getShingles() {
		return shingles;
	}

	public void setShingles(TreeSet<Shingle> shingles) {
		this.shingles = shingles;
	}
	
}
