<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link1 = "insertInputs.php";
	$link2 = "viewInputs.php";
	
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_URL, 
		'http://192.168.1.8:8080'
	);
	$content = curl_exec($ch);

	 // Create connection
	$conn = mysql_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysql_error());
	} 

	$sql = "INSERT INTO Inputs (input)
	VALUES ('".$content."');";

	mysql_select_db($dbname);
	$retval = mysql_query($sql, $conn);
	
	if(! $retval) {
		
		die('Could not execute statement: ' . mysql_error());
	}
	
	echo "Successfully inserted data";

	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link1.">Keep inserting</a>");
	echo nl2br ("\n<a href=".$link2.">View Table</a>");
?> 