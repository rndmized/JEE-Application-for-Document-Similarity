package ie.gmit.sw;
/**
 * 
 * @author rndmized
 * Job task class
 */
public class Job {
	
	private String taskNumber;
	private Similarity similarity;
	
	public Job(){
		super();
	}
	
	public Job(String taskNumber, Similarity similarity) {
		super();
		this.taskNumber = taskNumber;
		this.similarity = similarity;
	}

	
	public Similarity getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Similarity similarity) {
		this.similarity = similarity;
	}
	
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getTaskNumber() {
		return taskNumber;
	}



	
	


}

