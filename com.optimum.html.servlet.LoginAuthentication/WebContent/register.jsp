<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="RegisterController"
		enctype="multipart/form-data" class="form">
		<div class="table-responsive">
			<table class="table">
				<tbody>
					<tr>
						<td>Full Name</td>
						<td><input type="text" name="name" pattern="^([A-Za-z]+\s)*[A-Za-z]+$" required="" autofocus=""/></td>
					</tr>
					<tr>
						<td>Nric</td>
						<td><input type="text" name="nric"  pattern="^[A-Za-z]{1}[0-9]{7}[A-Za-z]{1}$" required="" autofocus=""/></td>
					</tr>
					<tr>
						<td>Gender</td>
						<td><input type="radio" name="gender" value="male" required="" autofocus="">
							Male<br> <input type="radio" name="gender" value="female" required="" autofocus="">
							Female<br></td>

					</tr>
					<tr>
						<td>Date of Birth</td>
						<td><input type="date" name="dob" required="" autofocus="" /></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><input type="text" name="address" required="" autofocus=""/></td>
					</tr>
					<tr>
						<td>Country</td>
						<td><select name="country" class="form-control">
								<option value="singapore">Singapore</option>
								<option value="malaysia">Malaysia</option>
								<option value="thailand">Thailand</option>
								<option value="indonesia">Indonesia</option>
								<option value="vietnam">Vietnam</option>
						</select></td>
					</tr>
					<tr>
						<td>Qualification</td>
						<td><select name="qualification" class="form-control">
								<option value="O'Levels">O'Levels</option>
								<option value="A'Levels">A'Levels</option>
								<option value="Diploma">Diploma</option>
								<option value="Degree">Degree</option>
								<option value="Masters">Masters</option>
						</select></td>
					</tr>
					<tr>
						<td>Attach Certificate</td>
						<td><input type="file" name="certificate" required="" autofocus=""></td>
					</tr>
					<tr>
						<td>Department</td>
						<td><select name="department" class="form-control" >
								<option value="IT">IT</option>
								<option value="Human Resource">Human Resource</option>
								<option value="Business">Business</option>
								<option value="Production">Production</option>
								<option value="Research and Development">Research and Development</option>
						</select></td>
					</tr>
					<tr>
						<td>Email Address</td>
						<td><input type="email" id="inputEmail" name="uname" class="form-control"
					placeholder="Email address" required="" autofocus=""/></td>
					</tr>
					<tr>
						<td>Mobile</td>
						<td><input type="text" name="mobile" pattern="[0-9]{8}" required="" autofocus=""/></td>
					</tr>
					<tr>
						<td>Employee ID</td>
						<td><input type="text" name="employeeid" pattern="[0-9]{0,5}" required="" autofocus=""/></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="Submit" /></td>

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