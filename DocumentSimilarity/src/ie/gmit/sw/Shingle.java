package ie.gmit.sw;

/**
 * Bean class containing a Document ID reference and Shingle's hashcode
 * 
 * @author RnDMizeD
 * @version 1.0a
 */
public class Shingle implements Comparable<Shingle> {

	private int hashcode;
	private String docId;

	/**
	 * Constructor
	 */
	public Shingle() {
		super();
	}

	/**
	 * Constructor taking a document ID and a hashcode
	 * 
	 * @param String
	 *            docId
	 * @param int
	 *            hashcode
	 */
	public Shingle(String docId, int hashcode) {
		super();
		this.hashcode = hashcode;
		this.docId = docId;
	}

	/**
	 * Return Shingle's hashcode
	 * 
	 * @return int haschode
	 */
	public int getHashcode() {
		return hashcode;
	}

	/**
	 * Set Shingle's hashcode to param int hashcode
	 * 
	 * @param int
	 *            hashcode
	 */
	public void setHashcode(int hashcode) {
		this.hashcode = hashcode;
	}

	/**
	 * Return Shingle's document ID.
	 * 
	 * @return String document's ID
	 */
	public String getDocId() {
		return docId;
	}

	/**
	 * Set Shingle's document ID to param String doc ID.
	 * 
	 * @param String
	 *            docId
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}

	@Override
	public int compareTo(Shingle o) {
		if (this.getHashcode() > o.getHashcode()) {
			return 1;
		} else if (this.getHashcode() < o.getHashcode()) {
			return -1;
		} else {
			return 0;
		}
	}

}
