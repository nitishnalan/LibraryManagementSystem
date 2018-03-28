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
<title>Availability Results</title>
</head>
<body>
<nav class="navbar navbar-default">
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
	<form action="" method="get">
		<%

			String searchfieldDisplay = (String) session.getAttribute("searchfieldDocument");
			int pageid = 1;
			int total = 5;
			if (pageid == 1) {
			} else {
				pageid = pageid - 1;
				pageid = pageid * total + 1;
			}
			List<Books> list = BooksDAO.updateStatusOfBooksInList(searchfieldDisplay);
			if(list.isEmpty()){
				out.print("No Book found for this search.");
			}else{
			//out.print("<h1>Page No: " + spageid + "</h1>");
			out.print("<table border='1' cellpadding='4' width='60%'>");
			out.print("<tr><th>Cover</th><th>Id</th><th>Name</th><th>Publisher</th><th>Pages</th><th>Authors</th></th><th>Status</th>");
			for (Books e : list) {
				out.print("<tr><td> <img src="+ e.getCover()+ "></td><td>"+ e.getIsbn() + "</td><td>" + e.getTitle() + "</td><td>"  + e.getPublisher() + "</td><td>" + e.getPages() + "</td><td>" + e.getAuthors()
						+ "</td> <td>"+ e.getStatus()+ "</td></tr>");
			}
			out.print("</table>");
			}
		%>

	</form>
</body>
</html>