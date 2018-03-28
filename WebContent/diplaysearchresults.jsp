<%@  page import = "java.util.List" language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import = "org.library.helpers.*,org.library.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type='text/css'>
<title>Book Search Results</title>
</head>

<%
	String table = (String) request.getAttribute("table");
%>
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
	<div class="col-lg-1 col-md-1 col-sm-12"></div>
	<div class="col-lg-10 col-md-10 col-sm-12"><p><h3>Book Search Results</h3><p>
	<%-- <%=table%> --%>
		<form action="borrow" method="get">
			<%
				
				int spageid = (int) session.getAttribute("page");
				String searchfieldDisplay = (String) session.getAttribute("searchfieldDocument");
				int pageid = 1;
				int total = 5;
				if (pageid == 1) {
				} else {
					pageid = pageid - 1;
					pageid = pageid * total + 1;
				}
				List<Books> list = BooksDAO.getRecords(pageid, total, searchfieldDisplay);
				if(list.isEmpty()){
					out.print("No Book found for this search.");
				}else{
				//out.print("<h1>Page No: " + spageid + "</h1>");
				out.print("<table  class='table table-striped' border='1' style='margin-top: 10px'>");
				out.print("<tr><th>Cover</th><th>Id</th><th>Name</th><th>Publisher</th><th>Pages</th><th>Authors</th><!--<th>Status</th>--><th>Borrow</th>");
				for (Books e : list) {
					out.print("<tr><td> <img src="+ e.getCover()+ "></td><td>"+ e.getIsbn() + "</td><td>" + e.getTitle() + "</td><td>"  + e.getPublisher() + "</td><td>" + e.getPages() + "</td><td>" + e.getAuthors()
							+ "</td> <!-- <td>"+ e.getStatus()+ "</td> --><td> <a href='borrow?bookId="+e.getIsbn()+"' class=\"btn btn-success\"> Borrow </td></tr>");
				}
				out.print("</table>");
				}
			%>
			<%-- 	<%<input type=\"submit\" value=\"Borrow\">  <input type=\"hidden\" name=\"hiddenIsbn\" value="+e.getIsbn()+
		int pgsize = (int) session.getAttribute("totalpagesneeded");
		out.print("pgsize " + pgsize);
	    for (int i = 0; i < pgsize; i++) {
		%>
			<a href="searchbook?pageNumber=<%=i+1%>"><%=i+1%>
		<%} %> --%>
		</form>
    </div>
    <div class="col-lg-1 col-md-1 col-sm-12"></div>
    </div>
</div>
</body>
</html>