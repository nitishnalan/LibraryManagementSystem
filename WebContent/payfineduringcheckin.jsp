<%@ page import = "java.util.List" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.library.helpers.*,org.library.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pay Fine And Check In Book</title>
</head>
<body>

    <form action = "checkinpay" method = "get">
	 The Book has a fine associated with this transaction, because the borrower has surpassed the due date for its submission.
     The Borrower shall have to pay the fine for the late submission. <br/>


	<%
	String loanIdCheckIn = (String) session.getAttribute("loanIdCheckIn");
    String bookIdCheckIn = (String) session.getAttribute("bookIdCheckIn");
   	String borrowerIdCheckIn = (String) session.getAttribute("borrowerIdCheckIn");

   	List<BookLoanFines> BLF = FinesDAO.getAmountduringCheckIn(loanIdCheckIn,bookIdCheckIn,borrowerIdCheckIn);
	out.print("<table border='1' cellpadding='4' width='85%'>");
	out.print("<tr><th>Book ID</th><th>Book Name</th><th>Borrower ID</th><th>Borrower Name</th><th>Check Out Date<br> (YYYY-MM-DD)</th>"+
	"<th>Due Date (YYYY-MM-DD)</th><th>Date-In (YYYY-MM-DD)</th><th>Fine Amount</th><th>Pay the fine and Check-In</th>");
	for (BookLoanFines e : BLF) {
	out.print("<tr><td>" + e.getBook_id() + "</td><td>" +e.getBook_name()+ "</td><td>"+ e.getBorrower_id() + 
	"</td><td>" + e.getBorrower_name()+ "</td><td>"  + e.getDate_out() + "</td><td>"  + e.getDue_date() + 
	"</td><td>"  + e.getDate_in() + "</td><td>"  + e.getFine_amount() + "</td><td> <a href='checkinpay?loanId="+e.getBook_loan_id() +"&borrowerId="+e.getBorrower_id()+"&bookId="+e.getBook_id()+"&payment=1'> Pay Fine </td></tr>");
	}
	out.print("</table>");
	%>

</body>
</html>