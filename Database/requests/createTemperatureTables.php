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

	$sql = "CREATE TABLE Temperature (
	id INT, temp VARCHAR(50), date TIMESTAMP,
	PRIMARY KEY(id, date),
	FOREIGN KEY(id) REFERENCES IPs(id)
	)";
	mysqli_query($conn, $sql);

	mysqli_close($conn);
	
	header("Location: createUserTables.php");

?>
