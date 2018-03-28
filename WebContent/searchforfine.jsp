<%@ page import = "java.util.List" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.library.helpers.*,org.library.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search For Fines</title>
</head>
<body>
<form action = "searchborrowerfine" method = "get">

	<%	
 		if(session.getAttribute("borrowerId") != null){
	 		String borrowerId = (String) session.getAttribute("borrowerId");
	 		out.print(borrowerId);
	 		if(borrowerId != null){
				int pageid = 1;
				int total = 5;
				if (pageid == 1) {
				} else {
					pageid = pageid - 1;
					pageid = pageid * total + 1;
				} 
				
				List<BookLoanFines> list = FinesDAO.getSummaryForBorrowerFineDetails(borrowerId);

				out.print("<table border='1' cellpadding='4' width='60%'>");
				out.print("<tr><th>Book ID</th><th>Book Name</th><th>Date Out</th><th>Due Date</th><th>Total Fine</th><th>Pay Fine</th>");
				for (BookLoanFines e : list) {
					out.print("<tr><td>" + e.getBook_id()+ "</td><td>" +e.getBook_name()+ "</td><td> $"+e.getDate_out()
					+ "</td><td>" + e.getDue_date()+ "</td><td>" + e.getFine_amount()+ "</td><td> <a href='borrowerfinedetails?bookLoanId="+e.getBook_loan_id()+"'> More Details </td></tr>");
				}
				out.print("</table>");
			}
 		}
	%>
</form>
</body>
</html>