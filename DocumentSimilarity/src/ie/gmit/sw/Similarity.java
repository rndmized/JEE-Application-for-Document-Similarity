package ie.gmit.sw;

import java.util.Map;

public interface Similarity {

	public String getDocID();
	public Map<String, Double> getSimilarity();
	public void setSimilarity(Map<String, Double> similarityMap);
	public boolean isProcessed();
	public void setProcessed();
}
