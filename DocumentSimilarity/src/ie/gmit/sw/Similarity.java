package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;

public class Similarity {
	
	private Document document;
	private Map<String, Double> similarityMap;
	private boolean isProcessed;
	
	public Similarity(Document document) {
		this.document = document;
		this.isProcessed = false;
		similarityMap = new HashMap<>();
	}
	
	public Document getDocument() {
		return this.document;
	}
	
	public boolean addSimilarity(String docID, double jaccardSimilarity) {
		similarityMap.put(docID, jaccardSimilarity);
		return true;
	}
	
	public Map<String, Double> getSimilarityMap() {
		return this.similarityMap;
	}
	
	public void setProcessed() {
		this.isProcessed = true;
	}
	
	public boolean isProcessed() {
		return this.isProcessed;
	}

}
