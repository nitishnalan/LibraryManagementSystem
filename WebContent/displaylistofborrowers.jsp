<%@ page import = "java.util.List" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.library.helpers.*,org.library.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Borrower Search Results</title>
</head>
<body>

	<h1>Borrower search Results</h1>
	
	<%
 	
		
	   	int spageid = (int) session.getAttribute("page");
 		String searchfieldDisplay = (String) session.getAttribute("searchfielddisplay");
		int pageid = 1;
		int total = 5;
		if (pageid == 1) {
		} else {
			pageid = pageid - 1;
			pageid = pageid * total + 1;
		} 
		
		List<Borrowers> list = BorrowersDAO.getRecords(pageid, total,searchfieldDisplay);
		
		//out.print("<h1>Page No: " + spageid + "</h1>");
		out.print("<table border='1' cellpadding='4' width='60%'>");
		out.print("<tr><th>ID</th><th>Name</th><th>Phone Number</th><th>Address</th>");
		for (Borrowers e : list) {
			out.print("<tr><td>" + e.getId() + "</td><td>" + e.getName() + "</td><td>" + e.getPhone()
					+ "</td><td>"  + e.getAddress() +"</td></tr>");
		}
		out.print("</table>");
	%>

	
</body>
</html>