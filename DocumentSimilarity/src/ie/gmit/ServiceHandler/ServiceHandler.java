package ie.gmit.ServiceHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ie.gmit.db.ObjectStorageAccessController;
import ie.gmit.db.ObjectStorageAccessRequest;
import ie.gmit.sw.Document;
import ie.gmit.sw.DocumentComparer;
import ie.gmit.sw.Job;
import ie.gmit.sw.Shingle;
import ie.gmit.sw.ShingleParser;
import ie.gmit.sw.Similarity;

/*
 * NOTE FROM STUDENT: This template has been barely modified.
 * 
 */


/* NB: You will need to add the JAR file $TOMCAT_HOME/lib/servlet-api.jar to your CLASSPATH 
 *     variable in order to compile a servlet from a command line.
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB. The file size in bytes after which the file will be
														// temporarily stored on disk. The default size is 0 bytes.
		maxFileSize = 1024 * 1024 * 10, // 10MB. The maximum size allowed for uploaded files, in bytes
		maxRequestSize = 1024 * 1024 * 50) // 50MB. he maximum size allowed for a multipart/form-data request, in bytes.
public class ServiceHandler extends HttpServlet {
	/*
	 * Declare any shared objects here. For example any of the following can be
	 * handled from this context by instantiating them at a servlet level: 1) An
	 * Asynchronous Message Facade: declare the IN and OUT queues or MessageQueue 2)
	 * An Chain of Responsibility: declare the initial handler or a full chain
	 * object 1) A Proxy: Declare a shared proxy here and a request proxy inside
	 * doGet()
	 */
	private int SHINGLE_SIZE = 0;
	private final int THREAD_POOL_SIZE = 50;
	private static long jobNumber = 0;
	private volatile ExecutorService ex;

	private volatile BlockingQueue<Job> inQueue;
	public static volatile Map<String, Similarity> outQueue;
	private static ObjectStorageAccessController dbc;
	private ArrayBlockingQueue<ObjectStorageAccessRequest> reqQueue;

	/*
	 * This method is only called once, when the servlet is first started (like a
	 * constructor). It's the Template Patten in action! Any application-wide
	 * variables should be initialised here. Note that if you set the xml element
	 * <load-on-startup>1</load-on-startup>, this method will be automatically fired
	 * by Tomcat when the web server itself is started.
	 */
	public void init() throws ServletException {
		ServletContext ctx = getServletContext(); // The servlet context is the application itself.

		// Reads the value from the <context-param> in web.xml. Any application scope
		// variables
		// defined in the web.xml can be read in as follows:
		SHINGLE_SIZE = Integer.parseInt(ctx.getInitParameter("SHINGLE_SIZE"));
		ex = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

		inQueue = new LinkedBlockingQueue<Job>();
		outQueue = new HashMap<String, Similarity>();
		reqQueue = new ArrayBlockingQueue<>(7);

		dbc = new ObjectStorageAccessController(reqQueue);
		Thread dbThread = new Thread(dbc, "DatabaseThread");
		dbThread.start();
		dbc.query(new ObjectStorageAccessRequest());
	}

	/*
	 * The doGet() method handles a HTTP GET request. Please note the following very
	 * carefully: 1) The doGet() method is executed in a separate thread. If you
	 * instantiate any objects inside this method and don't pass them around (ie.
	 * encapsulate them), they will be thread safe. 2) Any instance variables like
	 * environmentalVariable or class fields like jobNumber will are shared by
	 * threads and must be handled carefully. 3) It is standard practice for doGet()
	 * to forward the method invocation to doPost() or vice-versa.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Step 1) Write out the MIME type
		resp.setContentType("text/html"); 
		
		//Step 2) Get a handle on the PrintWriter to write out HTML
		PrintWriter out = resp.getWriter(); 
		
		//Step 3) Get any submitted form data. These variables are local to this method and thread safe...
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		Part part = req.getPart("txtDocument");
		// Create Shingle Parser and new Document
		ShingleParser sp = new ShingleParser(SHINGLE_SIZE);
		Document doc = new Document(title);
		//Parse document
		try {
			doc.setShingles((TreeSet<Shingle>) sp.parse(part.getInputStream(), title));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Step 4) Process the input and write out the response. 
		//The following string should be extracted as a context from web.xml 
		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		//We could use the following to track asynchronous tasks. Comment it out otherwise...
		if (taskNumber == null){
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
			//Add job to in-queue
			Job job = new Job();
			job.setTaskNumber(taskNumber);
			job.setSimilarity(new Similarity(doc));
			try {
				inQueue.put(job);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Run worker
			Runnable docComp = new DocumentComparer(inQueue, outQueue, dbc);
			ex.execute(docComp);
			
		}else{
			req.setAttribute("outQueue", null);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/poll");
			dispatcher.forward(req,resp);
			//Check out-queue for finished job with the given taskNumber
		}
		
		//Output some headings at the top of the generated page
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		
		
		//Output some useful information for you (yes YOU!)
		out.print("<div id=\"r\"></div>");
		out.print("<font color=\"#993333\"><b>");
		out.print("<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
		out.print("Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");				
		out.print("</b></font>");
		
		out.print("<h3>Compiling and Packaging this Application</h3>");
		out.print("Place any servlets or Java classes in the WEB-INF/classes directory. Alternatively package "); 
		out.print("these resources as a JAR archive in the WEB-INF/lib directory using by executing the ");  
		out.print("following command from the WEB-INF/classes directory jar -cf my-library.jar *");
		
		out.print("<ol>");
		out.print("<li><b>Compile on Mac/Linux:</b> javac -cp .:$TOMCAT_HOME/lib/servlet-api.jar WEB-INF/classes/ie/gmit/sw/*.java");
		out.print("<li><b>Compile on Windows:</b> javac -cp .;%TOMCAT_HOME%/lib/servlet-api.jar WEB-INF/classes/ie/gmit/sw/*.java");
		out.print("<li><b>Build JAR Archive:</b> jar -cf jaccard.war *");
		out.print("</ol>");
		
		//We can also dynamically write out a form using hidden form fields. The form itself is not
		//visible in the browser, but the JavaScript below can see it.
		out.print("<form name=\"frmRequestDetails\" action=\"poll\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	
		
		//JavaScript to periodically poll the server for updates (this is ideal for an asynchronous operation)
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); //Refresh every 5 seconds
		out.print("</script>");
		out.print("<font color=\"0000ff\">");	
		out.print("</font>");	
	}

	

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}