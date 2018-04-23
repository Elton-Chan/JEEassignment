<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.footer {
	position: fixed;
	left: 0;
	bottom: 0;
	width: 100%;
	background-color: white;
	text-align: center;
}
</style>
<title>User Login Page</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>



<body>

	<%@ page import="com.optimum.dao.UserDAOImpl"%>
	<%@ page import="com.optimum.pojo.User"%>
	<%@ page import="java.util.*"%>
	<%
		UserDAOImpl dao = new UserDAOImpl();
		User refUser = new User();
		Date lastAccessed = new Date(session.getLastAccessedTime());
		String userName = (String) session.getAttribute("user");

		
		
		if (userName != null) {
			
	%>
	<div class="header">
		<center>
			<img src="logo.png" alt="Optimum Solution Logo" class="img-fluid">
		</center>
	</div> 
	<div class="container" style="margin-top: 30px">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<strong>Welcome <%=userName%>
						</strong>
					</div>
					<br>
					<% if(dao.getEmpImage(userName) != null){ %>
					<img class="profile" src="ImageControllerServlet?emailAddress=<%=userName%>" alt="logo" height="100px"
						width="100px" />
					<%}else{ %>
					<img class="profile" src="blank.png"
						alt="logo" height="100px" width="100px" />
					<%} %>
					<div>
						Last Login Date and Time: <br>
						<%=lastAccessed%>
					</div>
					<div class="panel-body">
						<form action="LogoutServlet" method="post">
							<br>
							<button type="submit" class="btn btn-sm btn-default pull-right">Log
								out</button>
							<br> <br> <br> <br> <br> <br> <br>
							<br> <br> <br> <br> <br> <br> <a
								href="viewEmployee.jsp" target="iframe_admin"><u>View
									Employee Details</u></a><br> <a href="updateEmployee.jsp"
								target="iframe_admin"><u>Update Profile</u></a><br>
						</form>

					</div>
				</div>
			</div>
		</div>
		<iframe height="650px" width="60%" src="viewEmployee.jsp"
			name="iframe_admin" style="border: none;" style="margin-top: 0.5px"></iframe>
	</div>




	<div class="footer">
		<p>©Copyright Optimum Solutions Pte Ltd</p>
	</div>
	<%
		} else {
	%>
	<h3>Please Login first.</h3>
	<br>
	<form action="login.html" method="post">
		<input type="submit" value="Login Here">
	</form>
	<%
		}
	%>

</body>
</html>
