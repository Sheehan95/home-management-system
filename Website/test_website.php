<!DOCTYPE html>
<html>
	<head>
		<title>Home Management System</title>
		<link rel="stylesheet" type="text/css" href="HMSStyle.css">
		<link rel="icon" href="images/favicon.png">
	</head>
	<body>
		
		<?php
		/*file_get_contents("http://192.168.0.30:8080/temperature");
		echo $http_response_header[3] . "<br>";
		file_get_contents("http://192.168.0.30:8080/motion");
		echo "coordinates: { " . $http_response_header[3] . ", " . $http_response_header[4] . ", " . $http_response_header[5] . " }<br>";
		file_get_contents("http://192.168.0.30:8080/alarm");
		echo $http_response_header[3] . "<br>";*/
		
		if ($_POST["username"] != "employee" && $_POST["password"] != "employee") {
			header("Location: login.php");
			exit();
		}
		?>
		
		<div id="banner">
			<h2>Home Management System</h2>
			<div id="user"><img src="images/usericon.png" height="30" width="30"><?php echo $_POST["username"]; ?></div>
		</div>
		
		<table id="mainTable">
			<tr>
				<th><div class="tableText">ID</div></th>
				<th><div class="tableText">Owner</div></th>
				<th><div class="tableText">Address</div></th>
				<th><div class="tableText">Temperature</div></th>
				<th><div class="tableText">Motion</div></th>
				<th><div class="tableText">Alarm Status</div></th>
				<th></th>
			</tr>
		</table>
	</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script type="text/javascript" src="HMSScript.js"></script>
</html>