package ie.gmit.ServiceHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ie.gmit.sw.Similarity;

public class ServicePollHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 678L;

	private volatile Map<String, Similarity> outQueue;
	private boolean jobCompleted;

	public void init() throws ServletException {
		jobCompleted = false;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		DecimalFormat df = new DecimalFormat("0.00");
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");

		// I tried to send through the outQueue but it didn't work so I
		// ended up making it static, NOT good practice but I was running out of time.
		// outQueue = (Map<String, Similarity>) req.getAttribute("outQueue");
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
		out.print("<br>");

		// out.print("Place the final response here... a nice table (or graphic!) of the
		// document similarity...");
		// Check if out queue contains the task number
		if (outQueue.containsKey(taskNumber)) {
			// get similarity instance of it
			Similarity similarity = outQueue.get(taskNumber);
			// if similarity has been processed
			if (similarity.isProcessed()) {
				// the job is done, remove it from the out queue
				jobCompleted = true;
				outQueue.remove(taskNumber);

				Set<String> keys = similarity.getSimilarityMap().keySet();
				// for every document in the similarity map create a row in the table
				// with the document id and its similarity index
				if (keys.isEmpty()) {
					out.print("<H3>Document Database is empty, the submitted document has been added for future comparisons.</H3>");
				} else {
					// Create table
					out.print("<table width=\"66%\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\" align=\"center\"");
					out.print("<tr><th>Document Name</th><th>Similarity</th></tr>");
					for (String key : keys) {
						out.print("<tr><td align=\"center\">" + key + "</td>");
						out.print("<td align=\"center\">" + df.format(similarity.getSimilarityMap().get(key))
								+ "%</td></tr>");
					}
					out.print("</table>");

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
		// if job is not completed keep polling until it is
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