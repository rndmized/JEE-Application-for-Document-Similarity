package ie.gmit.sw;

public class Shingle implements Comparable<Shingle> {

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

	@Override
	public int compareTo(Shingle o) {
		if (this.hashcode > o.getHashcode()) {
			return 1;
		} else if (this.hashcode < o.getHashcode()) {
			return -1;
		} else {
			return 0;
		}
	}

}
