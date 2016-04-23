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
	temp VARCHAR(50), heating TINYINT(1), alarm TINYINT(1), 
	current VARCHAR(100)
	)";
	
//	$sql2 = "CREATE TABLE Users (
//	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
//	pi_ip VARCHAR(16)
//	)";
	
	mysql_select_db($dbname);
	$retval = mysql_query( $sql, $conn);
	//$retval2 = mysql_query( $sql2, $conn);

	if(! $retval ) {
      die('Could not create table: ' . mysql_error());
	}
	
//	if(! $retval2 ) {
//     die('Could not create table: ' . mysql_error());
//	}
	
	echo "Table created";

	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link.">Next</a>");

?>