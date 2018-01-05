package ie.gmit.sw;

public class TestRunner {

	public static void main(String[] args) {
		
		ObjectStorage db = new ObjectStorageImplementation();
		//System.out.println(db.addDocument(new Document("Test2")));
		db.deleteDocument(new Document("Test2"));
		System.out.println(db.loadDocumentList().toString());
		
		
		
	}
}
