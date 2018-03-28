<%@ page import = "java.util.List" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.library.helpers.*,org.library.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type='text/css'>
<title>List of borrowers</title>
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Library Management System</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="checkAvailability.jsp">Check Availability</a></li>
      <li class="active"><a href="#">Check Out Books</a></li>
      <li><a href="AddBorrower.jsp">Add Borrower</a></li>
      <li><a href="checkinbook.jsp">Check In Books</a></li>
      <li><a href="displayfines.jsp">Display Fines</a></li>
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
	<div class="row">
	<form action = "borrowersearch" method = "get">
	<div class="col-lg-4 col-md-4 col-sm-12"><p>Enter the ID or Name of the borrower, you would like to Search: </p></div>
	<div class="col-lg-4 col-md-4 col-sm-12">
		<div class="input-group">
		<input class="form-control" type ="text" name="searchfield" required>
		<span class="input-group-addon">
	    <i class="form-control-feedback glyphicon glyphicon-search"></i>
	    </span>
		</div>
	</div>
	<div class="col-lg-4 col-md-4 col-sm-12"><input type ="submit" class="btn btn-warning"></div>
	</form>
	</div>
	
	
	<%
 	/* 	String spageid = request.getParameter("page"); */
		
	   	//int spageid = (int) session.getAttribute("page");
 		String bookId = (String) session.getAttribute("bookID");
 		/* out.print("Book Id from get borrower jsp " + bookId); */
 		if(session.getAttribute("searchfieldBorrower") != null){
 		String searchfieldDisplay = (String) session.getAttribute("searchfieldBorrower");
 		if(searchfieldDisplay!=null){
		int pageid = 1;
		int total = 5;
		if (pageid == 1) {
		} else {
			pageid = pageid - 1;
			pageid = pageid * total + 1;
		} 
		
		List<Borrowers> list = BorrowersDAO.getRecords(pageid, total,searchfieldDisplay);
		
		out.print("<div class=\"row\"><div class=\"col-lg-1 col-md-1 col-sm-12\"></div>"
				+ "<div class=\"col-lg-10 col-md-10 col-sm-12\"><p  style='margin-top: 50px'><h3>Borrower Search Results</h3><p>");
		/* out.print("<h1>Page No: " + spageid + "</h1>"); */
		if(list.isEmpty()){out.print("No Search Results found for this user");}
		out.print("<table class='table table-striped' border='1' style='margin-top: 30px'");
		out.print("<tr><th>ID</th><th>Name</th><th>Phone Number</th><th>Address</th><th>Check Out</th>");
		for (Borrowers e : list) {
			out.print("<tr><td>" + e.getId() + "</td><td>" + e.getName() + "</td><td>" + e.getPhone()
					+ "</td><td>"  + e.getAddress() + "</td><td> <a href='checkout?borrowerId="+e.getId()+"&bookId="+bookId+"' class=\"btn btn-primary\"> Check Out </td></tr>");
		}
		out.print("</table>");
		}
 		}
	%>
	</p></div>
	<div class="col-lg-1 col-md-1 col-sm-12"></div>
	</div>
</div>
</body>
</html>