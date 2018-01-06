package ie.gmit.sw;

import java.util.Map;

public interface Similarity {

	Document getDocument();

	void setDocument(Document document);

	String getDocID();

	Map<String, Double> getSimilarity();

	void setSimilarity(Map<String, Double> similarityMap);

	boolean isProcessed();

	void setProcessed();

}