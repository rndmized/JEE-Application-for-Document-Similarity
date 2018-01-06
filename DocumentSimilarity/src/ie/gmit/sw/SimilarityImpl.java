package ie.gmit.sw;

import java.util.Map;
import java.util.Set;

public class SimilarityImpl implements Similarity {
	
	private Document document;
	private Map<String, Double> similarityMap;
	private boolean isProcessed;
	
	
	public SimilarityImpl(Document document) {
		super();
		this.document = document;
		this.similarityMap = null;
		this.isProcessed = false;
	}

	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#getDocument()
	 */
	@Override
	public Document getDocument() {
		return document;
	}

	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#setDocument(ie.gmit.sw.Document)
	 */
	@Override
	public void setDocument(Document document) {
		this.document = document;
	}
	
	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#getDocID()
	 */
	@Override
	public String getDocID() {
		return this.document.getDocID();
	}

	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#getSimilarity()
	 */
	@Override
	public Map<String, Double> getSimilarity() {
		return this.similarityMap;
	}

	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#setSimilarity(java.util.Map)
	 */
	@Override
	public void setSimilarity(Map<String, Double> similarityMap) {
		this.similarityMap = similarityMap;
	}

	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#isProcessed()
	 */
	@Override
	public boolean isProcessed() {
		return this.isProcessed;
	}

	/* (non-Javadoc)
	 * @see ie.gmit.sw.Similarity#setProcessed()
	 */
	@Override
	public void setProcessed() {
		this.isProcessed = true;

	}

}
