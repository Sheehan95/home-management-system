<?php

	$link1 = "http://localhost:8000/requests";

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";

	$conn = mysqli_connect($servername, $username, $password);
	
	if (! $conn) {
		die("Connection failed: " . mysqli_error($conn));
	}

	$sql = "DROP DATABASE " . $dbname;

	mysqli_select_db($conn, $dbname);
	$retval = mysqli_query($conn, $sql);

	if(! $retval) {
		
		die('Could not execute statement: ' . mysqli_error($conn));
	}

	echo "Successfully dropped database";

	mysqli_close($conn);

	echo nl2br ("\n<a href=".$link1.">HOME</a>");
?>
