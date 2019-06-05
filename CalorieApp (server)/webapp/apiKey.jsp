<dependency>
		<groupId>jstl</groupId>
		<artifactId>jstl</artifactId>
		<version>1.2</version>
	  </dependency>

<%-- At the beginning of the page, some of the necessary information is declared and the relevant stylesheets and scripts are linked. In the case of this assignment,
I have chosen to put an inline stylesheet into my code as there is not much CSS present. --%>
<html>
<head>
<html lang="en">
<meta charset="UTF-8">
<title>Vehicle Database</title>
<meta <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>>

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

</head>

<body>

<%-- The below HTML code is essentially an if/else statement, which displays which information is shown to the user on the web page.
A person who is not logged in will have access to different information compared to when the Admin is logged in --%>

	<c:choose>
<%-- The c:when test decides what the admin sees when they are logged in. --%>

			<c:when test="${loggedin == true}">

			<div class="container mb-2 bg-light text-dark">

				<h1>API Key</h1>

				<div class="image">
					<a href="./database"> <img src="./images/car.jpg" alt="car"
						height="150" width="150">
					</a>
				</div>
				
<%-- The logout button is a form which send a request to the logout jsp file. --%>

				<form action="${pageContext.request.contextPath}/logout"
					method="post">
					<input type="submit" class="d-inline btn btn-danger" value="Logout" />
				</form>
				<a href="./database" type="button" class="d-inline btn btn-success">Home</a>	
				<br><br><br>

	<h1>Hi: ${Users.username} </h1>
	
				<h2>Your API Key is: ${Users.apikey} </h2>

		</c:when>

<%-- Below "c:otherwise" is essentially the else part of an if/else statement,
it defines what is shown to a user visiting the page who is not logged in. --%>	

		<c:otherwise>


			<div class="container mb-2 bg-light text-dark">



					<h1>You are not authorised to visit this page.</h1>

						</div>

<%-- End of the opening otherwise statement, and also the end of the choose statement which loops over the entire page.--%>				

		</c:otherwise>

	</c:choose>

</body>



</html>