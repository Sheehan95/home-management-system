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
	id INT, username VARCHAR(16), password VARCHAR(16)
	PRIMARY KEY(id, username),
	FOREIGN KEY(id) REFERENCES IPs(id)
	);";

	$sql .= "CREATE TABLE Temperature (
	id INT, temp VARCHAR(50), date TIMESTAMP,
	PRIMARY KEY(id, date),
	FOREIGN KEY(id) REFERENCES IPs(id)
	)";

	if(mysqli_multi_query($conn, $sql)) {

		do {

			if($result = mysqli_store_result($conn)) {
		
				mysqli_query($conn, $result);

				mysqli_free_result($result);
			}
		} while (mysqli_next_result($conn));
	}

	mysqli_close($conn);
	
	header("Location: insertInputs.php");

?>
