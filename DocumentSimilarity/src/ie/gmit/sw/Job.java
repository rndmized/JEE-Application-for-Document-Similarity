package ie.gmit.sw;
/**
 * Job task class stores a task number and a similarity object.
 * 
 * @author rndmized
 * @version 1.0a
 */
public class Job {
	
	private String taskNumber;
	private Similarity similarity;
	/**
	 * Constructor
	 */
	public Job(){
		super();
	}
	/**
	 * Constructor taking task number and Similarity
	 * 
	 * @param taskNumber
	 * @param similarity
	 */
	public Job(String taskNumber, Similarity similarity) {
		super();
		this.taskNumber = taskNumber;
		this.similarity = similarity;
	}

	/**
	 * Returns Job's instance of similarity
	 * 
	 * @return Similarity
	 */
	public Similarity getSimilarity() {
		return similarity;
	}
	
	/**
	 * Set Job's Similarity to param Similarity
	 * 
	 * @param Similarity similarity
	 */
	public void setSimilarity(Similarity similarity) {
		this.similarity = similarity;
	}
	
	/**
	 * Set Job's task number to param String taskNumber
	 * 
	 * @param String taskNumber
	 */
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	/**
	 * Return Job's String Task Number.
	 * 
	 * @return String TaskNumber
	 */
	public String getTaskNumber() {
		return taskNumber;
	}



	
	


}

