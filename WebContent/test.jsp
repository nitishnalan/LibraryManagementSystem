<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type='text/css'>

<title>Test</title>
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Library Management System</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      <li><a href="checkAvailability.jsp">Check Availability</a></li>
      <li><a href="Search.jsp">Check Out Books</a></li>
      <li><a href="AddBorrower.jsp">Add Borrower</a></li>
      <li><a href="#">Check In Books</a></li>
    </ul>
  </div>
</nav>
<div class="container">
	<!-- <form action = "addborrowers" method="post"> -->
	<form id = "addborrower" action = "test" method="get">
		<div class="row">
		<div class="col-lg-2 col-md-2 col-sm-12"></div>
		<div class="col-lg-8 col-md-8 col-sm-12"><p><h3>Enter the details of the new user:</h3></p>
			<table  class='table table-striped' border='1' cellpadding='4' width='40%' style='margin-top: 10px'>
				<tr> 
					<td> <input type="text" class="form-control" name="name" required placeholder="Enter the Name"> </td> 
				</tr>
				<tr> 
					<!-- <td> <input type="text" pattern="[0-9]{9}" class="form-control" name="ssn" required placeholder="Enter 9 digit SSN"> </td> --> 
					<td> <input type="text" class="form-control" name="ssn" required placeholder="Enter 9 digit SSN"> </td>
				</tr>
				<tr> 
					<td> <input type="text" name="address" required class="form-control" placeholder="Enter the Address"> </td> 
				</tr>
				<tr> 
					<!-- <td> Phone : </td>  -->
					<td> <input type="text" name="phone" required class="form-control" placeholder="Enter the Phone No."> </td> 
				</tr>
				<tr>
					<td> <input type = "button" value= "Back" class="btn btn-warning">  <input type = "submit" value = "Submit"  class="btn btn-success">	<input type = "button" value="check" onclick="validate(name);" class="btn btn-info"> </td> 
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