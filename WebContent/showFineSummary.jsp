<%@page import = "java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.library.helpers.*,org.library.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type='text/css'>
<title>Fine Summary</title>
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Library Management System</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="checkAvailability.jsp">Check Availability</a></li>
      <li><a href="Search.jsp">Check Out Books</a></li>
      <li><a href="AddBorrower.jsp">Add Borrower</a></li>
      <li><a href="checkinbook.jsp">Check In Books</a></li>
      <li class="displayfines.jsp"><a href="#">Display Fines</a></li>
      <li><a href="#" data-toggle="modal" data-target="#surety">Run Fine Batch Job</a>
      	<div class="modal fade" id="surety">
      		<div class="modal-dialog">
      			<div class="modal-content">
      				<div class="modal-body">
      					<p>Are you sure you want to Run the batch job, and update Fine entries for today?</p>
      					<a href="runFineBatch" class="btn btn-primary">Yes</a> &nbsp &nbsp &nbsp &nbsp<a href="#" class="btn btn-danger" data-dismiss="modal">No</a>
      				</div>
      			</div>
      		</div>
      	</div>
      </li>
    </ul>
  </div>
</nav>

<div class="container">
<form>
</form>
	<div class="row">
	<div class="col-lg-1 col-md-1 col-sm-12"></div>
	<div class="col-lg-4 col-md-4 col-sm-12"><p>Details of the Borrower:  <br>
	<%	
		
 		if(session.getAttribute("borrowerId") != null){
	 		String searchfieldDisplay = (String) session.getAttribute("borrowerId");
	 		/* out.print(searchfieldDisplay); */
	 		if(searchfieldDisplay != null){
				int pageid = 1;
				int total = 5;
				if (pageid == 1) {
				} else {
					pageid = pageid - 1;
					pageid = pageid * total + 1;
				} 
				
				BorrowersDAO getName = new BorrowersDAO();
				String borrowerName = getName.getName(searchfieldDisplay);
				 List<BookLoanFines> list= FinesDAO.getSummaryForBorrowerFineDetails(searchfieldDisplay);
				
/* 				 out.print("<table class='table table-striped table-condensed' border=1 style='margin-top: 10px'>");
					out.print("<tr><th>Card Number</th><th>Borrower Name</th>");
					out.print("<tr><td>" + searchfieldDisplay+ "</td><td>" +borrowerName+ "</td></tr>");

					out.print("</table>");  */
								 
				out.print("<table class='table table-striped table-condensed' border=1 style='margin-top: 5px'>");
				out.print("<tr><th>Card Number : </th><td>" + searchfieldDisplay+ "</td></tr>");
				out.print("<tr><th>Borrower Name : </th><td>" +borrowerName+ "</td></tr>");
				out.print("</table>"); 
				
				%>
				</div>
				<div class="col-lg-7 col-md-7 col-sm-12"></div>
				</div>
				
				<div class="row">
				<div class="col-lg-1 col-md-1 col-sm-12"></div>
				<div class="col-lg-10 col-md-10 col-sm-12"><p style='margin-top: 70px'> Details of Outstanding Fines against Borrower: <br>
				<%
				out.print("<table class='table table-striped table-condensed' border=1 style='margin-top: 5px'>");
				out.print("<tr><th>Book ID</th><th>Book Name</th><th>Date Out</th><th>Due Date</th><th>Total Fine</th><th>Pay Fine</th>");
				for (BookLoanFines e : list) {
					out.print("<tr><td>" + e.getBook_id()+ "</td><td>" +e.getBook_name()+ "</td><td>"+e.getDate_out()
					+ "</td><td>" + e.getDue_date()+ "</td><td> $ " + e.getFine_amount()+ "</td><td> <a href='payfine?bookLoanId="+e.getBook_loan_id()+"' class=\"btn btn-primary\"> Pay Fine </td></tr>");
				}
				out.print("</table>");
			}
 		}
	%>
	</div>
	<div class="col-lg-1 col-md-1 col-sm-12"></div>
	</div>
</div>
</body>
</html>