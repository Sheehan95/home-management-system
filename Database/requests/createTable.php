<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link = "insertInputs.php";

	 // Create connection
	$conn = mysql_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysql_error());
	} 

	// sql to create table
	$sql = "CREATE TABLE Inputs (
	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
	input INT(6)
	)";
	
	mysql_select_db($dbname);
	$retval = mysql_query( $sql, $conn);

	if(! $retval ) {
      die('Could not create table: ' . mysql_error());
	}
	
	echo "Table created";

	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link.">Next</a>");

?>