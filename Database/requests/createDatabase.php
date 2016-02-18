<?php

    $servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link = "createTable.php";
	
    // Create connection
	$conn = mysql_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysql_error());
	}
   
	echo 'Connected successfully';
   
	$sql = 'CREATE Database raspberry';
	$create = mysql_query($sql, $conn);
   
	if(! $create ) {
		die('Could not create database: ' . mysql_error());
	}
   
	echo "Database ".$dbname." created successfully\n";
	
	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link.">Next</a>");
	
?>