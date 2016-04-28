<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link1 = "viewInputs.php";
	$link2 = "insertInputs.php";	

	$content1 = file_get_contents("http://localhost:8080/Temperature");
	$temp = json_decode($content1, true);
	
	$content2 = file_get_contents("http://localhost:8080/Alarm");
	$alarm = json_decode($content2, true);
	
	$content3 = file_get_contents("http://localhost:8080/Schedule");
	$schedule = json_decode($content3, true);

	$temperature = $temp['temperature'];

	$id = 1;


	 // Create connection
	$conn = mysqli_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysqli_error($conn));
	}
	
	mysqli_select_db($conn, $dbname);

	$sql = "INSERT INTO Temperature (id, temp, date)
	VALUES ('$id', '$temperature', NOW())";
	mysqli_query($conn, $sql);

	$sql2 = "INSERT INTO User_Info (id, username, password)
	VALUES ('$id', 'user1', 'password1');";
	mysqli_query($conn, $sql2);

	echo "Successfully inserted data";

	mysqli_close($conn);

	sleep(10);
	header("Location: insertInputs.php");
?> 
