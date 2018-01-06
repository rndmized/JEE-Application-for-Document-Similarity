package ie.gmit.ServiceHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ie.gmit.sw.Similarity;

public class ServicePollHandler extends HttpServlet {

	private volatile Map<String, Similarity> outQueue;
	private boolean jobCompleted;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		jobCompleted = false;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		//outQueue = (Map<String, Similarity>) req.getAttribute("outQueue");
		outQueue = ServiceHandler.outQueue;
		int counter = 1;
		if (req.getParameter("counter") != null) {
			counter = Integer.parseInt(req.getParameter("counter"));
			counter++;
		}

		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		out.print("<b><font color=\"ff0000\">A total of " + counter
				+ " polls have been made for this request.</font></b> ");
		out.print("Place the final response here... a nice table (or graphic!) of the document similarity...");
		if (outQueue.containsKey(taskNumber)) {
			Similarity similarity = outQueue.get(taskNumber);
			if (similarity.isProcessed()) {
				jobCompleted = true;
				outQueue.remove(taskNumber);
				Set<String> keys = similarity.getSimilarityMap().keySet();
				for (String key : keys) {
					out.print("<p>Against " + key + ": " + similarity.getSimilarityMap().get(key)+"</p>");
				}
			}
		}
		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		if (!jobCompleted) {
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); // Refresh every 5
																							// seconds
			out.print("</script>");
		}
		

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}