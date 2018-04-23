<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="com.optimum.dao.UserDAOImpl"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.css">
  
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>
</head>
<body>
  <script>$(document).ready( function () {
    $('#table_R').DataTable({
    	 "aLengthMenu": [[5, 10, -1], [5, 10, "All"]]
    });
    
} );</script>
	<div class="table-responsive">
		<table class="table" id="table_R">
			<thead>
				<tr>
					<th>#</th>
					<th>Name</th>
					<th>Employee ID</th>
					<th>status</th>
					<th> </th>
				</tr>
			</thead>
			<%
				UserDAOImpl dao = new UserDAOImpl();
				ArrayList<String> a = dao.lockName();
				ArrayList<String> b = dao.lockEmp();
				ArrayList<String> c = dao.lockStatus();
			%>

			<tbody>
				<%
					for (int i = 0; i < a.size(); i++) {
				%>
				<tr>
					<th>
						<%
							out.println(i+1);
						%>
					</th>
					<th>
						<%
							out.println(a.get(i) + "      ");
						%>
					</th>
					<th>
						<%
							out.println(b.get(i) + "      ");
						%>
					</th>
					<th> 
						<%
							out.println(c.get(i) + "      ");
						%>
					</th>
					<th>
					<form method="post" action="LockUser"> 
						<input type="submit" value="Click to Lock/Unlock"/>
						<input type="hidden" name="emp" value="<%=b.get(i)%>" />
					</form>
					</th>
				</tr>




				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>