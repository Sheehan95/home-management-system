<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";

	 // Create connection
	$conn = mysqli_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysqli_error($conn));
	} 

	mysqli_select_db($conn, $dbname);
	$sql = "INSERT INTO IPs (id, pi_ip)
	VALUES ('1', 'localhost:8080');";
	mysqli_query($conn, $sql);

	mysqli_close($conn);

	header("Location: createTemperatureTables.php");
?>
