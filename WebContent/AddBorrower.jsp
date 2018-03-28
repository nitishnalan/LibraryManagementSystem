<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type='text/css'>

<title>Add a borrower</title>
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
      <li class="active"><a href="#">Add Borrower</a></li>
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
	<form action = "addborrowers" method="post"> 
	<!-- <form id = "addborrower" action = "test" method="get"> -->
		<div class="row">
		<div class="col-lg-2 col-md-2 col-sm-12"></div>
		<div class="col-lg-8 col-md-8 col-sm-12"><p><h3>Enter the details of the new user:</h3></p>
			<table  class='table table-striped' border='1' cellpadding='4' width='40%' style='margin-top: 10px'>
				<tr> 
					<td> <input type="text" class="form-control" name="name" required placeholder="Enter the Name"> </td> 
				</tr>
				<tr> 
					<!-- <td> <input type="text" pattern="[0-9]{9}" class="form-control" name="ssn" required placeholder="Enter 9 digit SSN"> </td> --> 
					<td> <input type="text" class="form-control" name="ssn" pattern="[0-9]{9}" required placeholder="Enter 9 digit SSN"> </td>
				</tr>
				<tr> 
					<td> <input type="text" name="address" required class="form-control" placeholder="Enter the Address"> </td> 
				</tr>
				<tr> 
					<!-- <td> Phone : </td>  -->
					<td> <input type="text" name="phone" pattern="[0-9]{10}" required class="form-control" placeholder="Enter the Phone No."> </td> 
				</tr>
				<tr>
					<td> <input type = "button" value= "Back" class="btn btn-warning">  <input type = "submit" value = "Submit"  class="btn btn-success"></td> 
				</tr>
			</table>
			
			
			</div>
			<div class="col-lg-2 col-md-2 col-sm-12"></div>
		</div>
	</form>
</div>

<script type="text/javascript">
	function validate(){
/* 		document.getElementById("addborrower").action = "/test";
		document.getElementById("addborrower").method = "GET"; */
		document.getElementById("addborrower").submit();
	}
</script>
</body>
</html>