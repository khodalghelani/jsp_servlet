<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Uodate Student</title>
	
		<link type="text/css" rel="stylesheet" href="css/style.css" />	
		<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
	
	</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>University of Massachussets, Boston</h2>
		</div>
	</div>

	<div id="container">
		<div>
			<h3>Add Student</h3>
			<form action="StudentControllerServlet" method="GET">
			
				<input type="hidden" name="command" value="UPDATE" />
				
				<input type="hidden" name="studentId" value="${THE_STUDENT.id }" />
				
				<table>
					<tbody>
						
						<tr>
							<td><label>First Name :</label></td>
							<td><input type="text" name="firstName" 
									value="${THE_STUDENT.firstName }"/></td>
						</tr>
						
						<tr>
							<td><label>Last Name :</label></td>
							<td><input type="text" name="lastName" 
									value="${THE_STUDENT.lastName }"/></td>
						</tr>
						
						<tr>
							<td><label>Email :</label></td>
							<td><input type="text" name="email" 
									value="${THE_STUDENT.email }"/></td>
						</tr>
						
						<tr>
							<td><label></label></td>
							<td><input type="submit" value="Save" class="save" /></td>
						</tr>
						
					</tbody>
				</table>	
			</form>
			
			<a href="StudentControllerServlet">Back to List</a>
			
		</div>
	</div>
</body>
</html>