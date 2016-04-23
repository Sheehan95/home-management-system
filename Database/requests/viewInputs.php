<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link1 = "insertInputs.php";
	$link2 = "http://localhost:8000/requests";

	 // Create connection
	$conn = mysql_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysql_error());
	}

	// sql to create table
	$sql = "SELECT * FROM Inputs;";
	//$sql2 = "SELECT * FROM Users";
	 
	mysql_select_db($dbname);
	$result = mysql_query($sql, $conn);
	//$result2 = mysql_query($sql2, $conn);

	echo "Inputs:<br><br>";
	if (mysql_num_rows($result) > 0) {
		
    // output data of each row
		while($row = mysql_fetch_assoc($result)) {
			echo $row["id"] . " &nbsp &nbsp " . $row["temp"]. " &nbsp &nbsp " . $row["heating"] . 
			" &nbsp &nbsp " . $row["alarm"] . " &nbsp &nbsp " . $row["current"] . "<br>";
		}
	} else {
		
		echo "0 results";
	}
	
	/*
	echo "Users:<br><br>";
	echo "ID\tPi IP";
	if (mysql_num_rows($result2) > 0) {
		
    // output data of each row
		while($row = mysql_fetch_assoc($result2)) {
			echo "id: " . $row["id"]. " - Input: " . $row["pi_ip"]. "<br>";
		}
	} else {
		
		echo "0 results";
	}
	*/

	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link1.">Back to inserting</a>");
	echo nl2br ("\n<a href=".$link2.">HOME</a>");
?>