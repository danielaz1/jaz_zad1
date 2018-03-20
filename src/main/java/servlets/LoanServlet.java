package servlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import domain.Installment;
import domain.LoanParameters;
import services.LoanService;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/loan")
public class LoanServlet extends HttpServlet {

	private LoanService loanService;

	public void init() {
		loanService = new LoanService();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (! checkParameters(request)) {
			response.getWriter().print("Nie uzupelniono wszystkich pol");
			return;
		}

		List<Installment> installments = loanService.getInstallments(getLoanParametersFromRequest(request));

		String table = buildTable(installments);

		if (request.getParameter("pdf") == null) {
			response.setContentType("text/html");
			response.getWriter().write(table);
		} else {
			createPdf(table, response);
		}
	}

	private boolean checkParameters(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();

		while (params.hasMoreElements()) {
			String parameter = request.getParameter(params.nextElement());
			if (parameter == null || parameter.equals("")) {
				return false;
			}
		}
		return true;
	}

	private LoanParameters getLoanParametersFromRequest(HttpServletRequest request) {
		LoanParameters parameters = new LoanParameters();

		parameters.setAmount(Double.parseDouble(request.getParameter("amount")));
		parameters.setNumberOfInstallments(Integer.parseInt(request.getParameter("number")));
		parameters.setInterestRate(Double.parseDouble(request.getParameter("interest")));
		parameters.setFee(Double.parseDouble(request.getParameter("fee")));
		parameters.setConstant(request.getParameter("type").equals("constant"));

		return parameters;
	}

	private String buildTable(List<Installment> installments) {
		StringBuilder table = new StringBuilder();

		table.append("<table>");
		table.append("<tr>  <th>Numer raty </th> <th>Kwota kapitalu </th>  <th>Kwota odsetek </th>  <th>Oplaty stale </th> " +
				"<th>Kwota do splaty </th> </tr>");

		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		for (Installment installment : installments) {
			table.append("<tr>  <td> ").append(installment.getNumber())
					.append("</td> <td>").append(df.format(installment.getAmountOfCapital()))
					.append("</td> <td>").append(df.format(installment.getAmountOfInterest()))
					.append("</td> <td>").append(df.format(installment.getFixedFees()))
					.append("</td> <td>").append(df.format(installment.getAmount()))
					.append("</td> </tr>");
		}
		table.append("</table>");
		return table.toString();
	}

	private void createPdf(String table, HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		ServletOutputStream out = response.getOutputStream();

		Document document = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			InputStream is = new ByteArrayInputStream(table.getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			document.close();
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
	}

}

