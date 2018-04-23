<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="UpdateProfile"
		enctype="multipart/form-data" class="form">
		<%
			String userName = (String) session.getAttribute("user");
		%>
		<input type="hidden" name="userName" value="<%=userName%>" />
		<div class="table-responsive">
			<table class="table">
				<tbody>
					<tr>
						<td>New Password</td>
						<td><input type="text" name="password" /></td>
						<td><input type=submit name="button" value="Update Password" /></td>
					</tr>
					<tr>
						<td>New Address</td>
						<td><input type="text" name="address" /></td>
						<td><input type="submit" name="button" value="Update Address" /></td>
					</tr>
					<tr>
						<td>New Qualification</td>
						<td><select name="qualification" class="form-control">
								<option value="O'Levels">O'Levels</option>
								<option value="A'Levels">A'Levels</option>
								<option value="Diploma">Diploma</option>
								<option value="Degree">Degree</option>
								<option value="Masters">Masters</option>
						</select></td>
						<td><input type="submit" name="button" value="Update Qualification" /></td>
					</tr>
					<tr>
						<td>New Certificate</td>
						<td><input type="file" name="certificate"></td>
						<td><input type="submit" name="button" value="Update Certificates" /></td>
					</tr>
					<tr>
						<td>Update Profile Picture</td>
						<td><input type="file" name="profilepic"></td>
						<td><input type="submit" name="button" value="Update Picture" /></td>
					</tr>
					<tr>
						<td>New Email Address</td>
						<td><input type="email" id="inputEmail" name="email" autofocus=""  class="form-control"
					placeholder="Email address"/></td>
						<td><input type="submit" name="button" value="Update Email" /></td>
					</tr>
					<tr>
						<td>New Mobile</td>
						<td><input type="text" name="mobile" pattern="[0-9]{8}" /></td>
						<td><input type="submit" name="button" value="Update Mobile" /></td>
					</tr>

				</tbody>
			</table>
		</div>
	</form>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>