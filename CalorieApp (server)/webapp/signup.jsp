<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/site.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	
<%--Inline CSS Styling --%>

<style>
body {
  background-color: #d9e6f2;
}

h1 {
  color: #336699;
  margin-left: 40px;
  font-size: 48px;
} 

h2 {
  color: #336699;
  text-align: center;
} 

th, td {
  color: #336699;
  margin-left: 40px;
  font-size: 13px;
}


p {
  color: maroon;
  margin-left: 40px;
  font-size: 8px;
} 

.table td.fit, 
.table th.fit {
    white-space: nowrap;
    width: 1%;
    font-size: 48px;
  
}

<!-- The below CSS considers that if the database table spills over the borders of the webpage,
 the layout will stay consistent and only the table will stretch the page. -->

.table-wrapper-scroll-x {
display: block;
max-width:	100%;
overflow-x: auto;
-ms-overflow-style: -ms-autohiding-scrollbar;
width: 100% !important;
font-size: 10px; !important;

}

</style>


<title>Register as a new user</title>
</head>
<body>

			<div class="container mb-2 bg-light text-dark">

				<h2>Create a new user account!</h2>


<form method="POST" action="./signup">
		<div class="form-group">
			<label for="make">First Name</label> <input type="text"
				class="form-control" id="firstname" name="firstname"
				placeholder="John" required>
		</div>
		<div class="form-group">
			<label for="model">Last Name</label> <input type="text"
				class="form-control" id="lastname" name="lastname"
				placeholder="Barkley" required>
		</div>
		<div class="form-group">
			<label for="year">User Name</label> <input type="text"
				class="form-control" min=1900 id="username" name="username"
				placeholder="JBarkley2" required>
		</div>
		<div class="form-group">
			<label for="price">Password</label> <input type="password"
				class="form-control" min=0 id="password" name="password"
				placeholder="*******" required>
		</div>
		
		<div class="form-group">
			<label for="price">Email</label> <input type="text"
				class="form-control" min=0 id="email" name="email"
				placeholder="test@test.com" required>
		</div>
		
		<button type="submit" class="btn btn-primary btn-lg btn-block">Finished! Create my account</button>
		<br>

		</form>
		
		</div>

</body>
</html>