<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link1 = "insertInputs.php";
	$link2 = "http://localhost:8080/requests";

	 // Create connection
	$conn = mysql_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysql_error());
	}

	// sql to create table
	$sql = "SELECT * FROM Inputs;";
	 
	mysql_select_db($dbname);
	$result = mysql_query($sql, $conn);

	if (mysql_num_rows($result) > 0) {
		
    // output data of each row
		while($row = mysql_fetch_assoc($result)) {
			echo "id: " . $row["id"]. " - Input: " . $row["input"]. "<br>";
		}
	} else {
		
		echo "0 results";
	}

	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link1.">Back to inserting</a>");
	echo nl2br ("\n<a href=".$link2.">HOME</a>");
?>