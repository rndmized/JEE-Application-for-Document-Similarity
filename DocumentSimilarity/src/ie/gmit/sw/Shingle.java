package ie.gmit.sw;

public class Shingle {

	private int hashcode;
	private String docId;

	public Shingle() {
		super();
	}

	public Shingle(String docId, int hashcode) {
		super();
		this.hashcode = hashcode;
		this.docId = docId;
	}

	public int getHashcode() {
		return hashcode;
	}

	public void setHashcode(int hashcode) {
		this.hashcode = hashcode;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

}
