package ie.gmit.sw;

import java.util.TreeSet;

/**
 * Bean class to store document ID and a set of Shingles
 * 
 * @author RnDMizeD
 *
 */
public class Document {

	private String docID;
	private TreeSet<Shingle> shingles;

	/**
	 * Constructor
	 */
	public Document() {
		super();
	}

	/**
	 * Constructor taking String ID for document
	 * 
	 * @param docID
	 */
	public Document(String docID) {
		super();
		this.docID = docID;
	}

	/**
	 * Returns document ID.
	 * 
	 * @return String documentID
	 */
	public String getDocID() {
		return docID;
	}

	/**
	 * Set document ID to param ID
	 * 
	 * @param docID
	 */
	public void setDocID(String docID) {
		this.docID = docID;
	}

	/**
	 * Returns set of Shingles for this document
	 * 
	 * @return Set of Shingles
	 */
	public TreeSet<Shingle> getShingles() {
		return shingles;
	}

	/**
	 * Sets Shingle set to param set of Shingles
	 * 
	 * @param Set<Shingle>
	 *            shingles
	 */
	public void setShingles(TreeSet<Shingle> shingles) {
		this.shingles = shingles;
	}

}
