import java.io.File;
import java.io.IOException; 
import javax.servlet.*; 
import javax.servlet.http.*; 

import java.net.URL;
import java.util.Set;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.rtf.RTFReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

public class Sample extends HttpServlet { 

	// servlet initialization method
	static int reportNum = 0;
	public void init(ServletConfig config) throws ServletException {
		super.init(config); 
		
		// Initialize the Reporting Engine 
		
		ClassicEngineBoot.getInstance().start();
	} 

	// the doGet method handles all the requests
	// received by this servlet
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
		
		// Handle Pentaho Report Request
		
		try {
			// load report definition
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			
			System.out.println("#################Report Name############ " + request.getParameter("report"));
			System.out.println("XXXXXXXXXXXX"+this.getServletContext().getRealPath("/"));
			String reportPath = "file:" + this.getServletContext().getRealPath(request.getParameter("report"));
			Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();
			
			// determine the output format and render accordingly
			String outputFormat = request.getParameter("outputFormat");
			//System.out.println(request.getParameter("AccountNature"));
			String accountNature = request.getParameter("AccountNature");
			report.getParameterValues().put("AccountNature", accountNature);
			
			if (accountNature != null) {
				 report.getParameterValues().put("AccountNature",accountNature);
			}
			
			String accountGroup = request.getParameter("AccountGroup");
			report.getParameterValues().put("AccountGroup", accountNature);
			if (accountGroup != null) {
				 report.getParameterValues().put("AccountGroup",accountGroup);
			}
			
			String transactionHead = request.getParameter("TransactionHead");
			report.getParameterValues().put("TransactionHead", transactionHead);
			if (transactionHead != null) {
				 report.getParameterValues().put("TransactionHead",transactionHead);
			}
			
			String childHead = request.getParameter("ChildHead");
			report.getParameterValues().put("ChildHead", childHead);
			if (childHead != null) {
				 report.getParameterValues().put("ChildHead",childHead);
			}
			
			String from = request.getParameter("From");
			report.getParameterValues().put("From", from);
			if (childHead != null) {
				 report.getParameterValues().put("From",from);
			}
			
			String to = request.getParameter("To");
			report.getParameterValues().put("To", to);
			if (childHead != null) {
				 report.getParameterValues().put("To",to);
			}

			System.out.println("Output format "+outputFormat);
			System.out.println("accountNature "+accountNature);
			System.out.println("accountGroup "+accountGroup);
			System.out.println("childHead "+childHead);
			System.out.println("transactionHead "+transactionHead);
			System.out.println("from "+from);
			System.out.println("to "+to);
			if ("pdf".equals(outputFormat)) {
				// render in pdf
				response.setContentType("application/pdf");
				//response.setHeader( "Content-Disposition", "attachment;filename="
				//	     + "test" );
				PdfReportUtil.createPDF(report, response.getOutputStream());
			} else if ("xls".equals(outputFormat)) {
				// render in excel
				response.setContentType("application/vnd.ms-excel");
				//response.setHeader( "Content-Disposition", "attachment;filename="
				//	     + "test" );
				ExcelReportUtil.createXLS(report, response.getOutputStream());
			} else
			
			{
				// render in HTML
				String reportLoc = "report_" + reportNum++;
				String path = this.getServletContext().getRealPath(reportLoc);
				File folder = new File(path);
				folder.mkdir();
				HtmlReportUtil.createDirectoryHTML(report, path
						+ File.separator + "index.html");
				response.sendRedirect(reportLoc + "/index.html");

				//response.setContentType("text/html");
				//response.setHeader( "Content-Disposition", "attachment;filename="
				//	     + "test" );
				//HtmlReportUtil.createStreamHTML(report, response.getOutputStream());
			}

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (ReportProcessingException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// the doPost method simply calls the doGet method

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
		doGet(request, response); 
	} 
}
