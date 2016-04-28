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

	$sql = "CREATE TABLE User_Info (
	id INT, username VARCHAR(16), password VARCHAR(16),
	PRIMARY KEY(id),
	FOREIGN KEY(id) REFERENCES IPs(id)
	)";
	mysqli_query($conn, $sql);

	mysqli_close($conn);
	
	header("Location: insertInputs.php");

?>
