package ie.gmit.db;

import java.util.List;

import ie.gmit.sw.Document;

public interface ObjectStorage {

	public boolean addDocument(Document doc);

	public List<Document> loadDocumentList();

	public void deleteDocument(Document d);
	
	public void close();

}