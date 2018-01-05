package ie.gmit.sw;

import java.util.List;

public interface ObjectStorage {

	boolean addDocument(Document doc);

	List<Document> loadDocumentList();

	void deleteDocument(Document d);

}