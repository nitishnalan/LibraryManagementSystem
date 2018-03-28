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
<title>Display Fines</title>
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
      <li class="active"><a href="#">Display Fines</a></li>
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
<form action = "displayfine" method = "get">
<div class="row">
		<div class="col-lg-4 col-md-4 col-sm-12"><p><h5>Enter the Card Number you would like to Search: </h5></div>
		<div class="col-lg-4 col-md-4 col-sm-12">
			<div class="input-group">
			<input class="form-control" type ="text" name="searchfield" placeholder="Enter your search string here" required>
			<span class="input-group-addon">
			<i class="form-control-feedback glyphicon glyphicon-search"></i>
			</span>
			</div>
		</div>
	<div class="col-lg-4 col-md-4 col-sm-12">
		<input type="submit" class="btn btn-warning">
	</div>
</div>
</form>	

	<div class="row">
	<div class="col-lg-2 col-md-2 col-sm-12"></div>
	<div class="col-lg-8 col-md-8 col-sm-12">
		<%	
		
 		if(session.getAttribute("searchFieldFinesSearch") != null){
	 		String searchfieldDisplay = (String) session.getAttribute("searchFieldFinesSearch");
	 		/* out.print(searchfieldDisplay); */
	 		if(searchfieldDisplay != null){
				int pageid = 1;
				int total = 5;
				if (pageid == 1) {
				} else {
					pageid = pageid - 1;
					pageid = pageid * total + 1;
				} 
				List<Borrowers> list;
				if(searchfieldDisplay.toLowerCase().matches("all")){
					 list = BorrowersDAO.getTotalFineRecords(searchfieldDisplay);
				}else
				{
					 list = BorrowersDAO.getSpecificFineRecords(searchfieldDisplay);
				}
				
				if(list.isEmpty()){
					/* out.print("No Book found for this search."); */
				}else{
				/* out.print("<h1>Page No: " + spageid + "</h1>"); */
				out.print("<table class='table table-striped table-condensed' border=1 style='margin-top: 50px'>");
				out.print("<tr><th>Card Number</th><th>Name</th><th>Total Fine</th><th>Details</th>");
				for (Borrowers e : list) {
					out.print("<tr><td>" + e.getId()+ "</td><td>" +e.getName()+ "</td><td> $ "+e.getTotalFine()
					+ "</td><td> <a href='borrowerfinedetails?borrowerId="+e.getId()+"' class=\"btn btn-primary\"> More Details </td></tr>");
				}
				out.print("</table>");
				}
			}
 		}
	%>
	</div>
	<div class="col-lg-2 col-md-2 col-sm-12"></div>
	</div>
</div>
</body>
</html>