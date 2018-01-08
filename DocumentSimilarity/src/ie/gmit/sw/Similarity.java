package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
/**
 * Contains a Document and a Mapping of documents IDs and their similarity with this document.
 * 
 * @author RnDMizeD
 * @version 1.0b
 */
public class Similarity {
	
	private Document document;
	private Map<String, Double> similarityMap;
	private boolean isProcessed;
	
	/**
	 * Constructor taking a Document as Parameter
	 * 
	 * @param Document document
	 */
	public Similarity(Document document) {
		this.document = document;
		//Instantiate Variable and Map
		this.isProcessed = false;
		similarityMap = new HashMap<>();
	}
	
	/**
	 * Return Similarity's Document
	 * 
	 * @return Document document
	 */
	public Document getDocument() {
		return this.document;
	}
	
	/**
	 * Adds similarity of a document to the Map
	 * 
	 * @param String docID
	 * @param double jaccardSimilarity
	 */
	public void addSimilarity(String docID, double jaccardSimilarity) {
		similarityMap.put(docID, jaccardSimilarity);
	}
	
	/**
	 * Returns similarity map containing Document IDs and their similarity with this document.
	 * 
	 * @return Map<String, Double> similarity Map
	 */
	public Map<String, Double> getSimilarityMap() {
		return this.similarityMap;
	}
	
	/**
	 * Set Processed status to true
	 * 
	 */
	public void setProcessed() {
		this.isProcessed = true;
	}
	
	/**
	 * Returns Similarity status
	 * 
	 * @return true if Similarity has been processed
	 */
	public boolean isProcessed() {
		return this.isProcessed;
	}

}
