<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type='text/css'>
<title>Library Management System</title>
</head>
	<% String messageToDisplayOne = (String)session.getAttribute("Message1"); 
	%>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Library Management System</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="checkAvailability.jsp">Check Availability</a></li>
      <li><a href="Search.jsp">Check Out Books</a></li>
      <li class="AddBorrower.jsp"><a href="#">Add Borrower</a></li>
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
	<div class="col-lg-2 col-md-2 col-sm-12"></div>
	<div class="col-lg-8 col-md-8 col-sm-12"><p><h3>
	Please read the message: </h3><br/>
	<p>
	<h2>
	<%= messageToDisplayOne %>
	</h2>
	</div>
	<div class="col-lg-2 col-md-2 col-sm-12"></div>
	</div>
</div>

</body>
</html>