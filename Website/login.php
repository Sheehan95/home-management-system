<!DOCTYPE html>
<html>
	<head>
		<title>Home Management System</title>
		<link rel="stylesheet" type="text/css" href="loginStyle.css">
		<link rel="icon" href="images/favicon.png">
	</head>
	<body>
		<div id="banner">
			<h2>Home Management System</h2>
		</div>
		
		<div id="loginContent">
			<form action="test_website.php" method="post">
				Username:<br>
				<input type="text" name="username" id="username"><br>
				Password:<br>
				<input type="password" name="password" id="password"><br><br>
				<input type="submit" value="Submit" id="submit">
			</form>
		</div>
	</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script type="text/javascript" src="loginScript.js"></script>
</html>