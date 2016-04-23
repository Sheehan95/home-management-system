<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link1 = "insertInputs.php";
	$link2 = "viewInputs.php";
	
	
	
	$content1 = file_get_contents("http://localhost:8080/Temperature");
	$temp = json_decode($content1, true);
	
	$content2 = file_get_contents("http://localhost:8080/Alarm");
	$alarm = json_decode($content2, true);
	
	$content3 = file_get_contents("http://localhost:8080/Schedule");
	$schedule = json_decode($content3, true);

/*	
	$current_date = $temp['date']['year'] . "-";
	$current_date .= $temp['date']['month'] . "-";
	$current_date .= $temp['date']['day'] . " ";
	$current_date .= $temp['date']['hour'] . ":";
	$current_date .= $temp['date']['minute'] . ":";
	$current_date .= $temp['date']['second'];
*/
	$current_date = "2016-04-23 18:11:56";
	$temperature = $temp['temperature'];
	$heating = (int)$temp['heating'];
	
	$alarm_on = (int)$alarm['alarm_armed'];
/*	
	$task_type = $schedule['task_type'];
	$scheduled_date = $schedule['date']['day'] . "/";
	$scheduled_date .= $schedule['date']['month'] . "/";
	$scheduled_date .= $schedule['date']['year'] . " ";
	$scheduled_date .= $schedule['date']['hour'] . ":";
	$scheduled_date .= $schedule['date']['minute'] . ":";
	$scheduled_date .= $schedule['date']['second'] . ":";
	$scheduled_date .= $schedule['date']['microsecond'];
*/
	 // Create connection
	$conn = mysql_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysql_error());
	} 

	$sql = "INSERT INTO Inputs ". "(temp, heating, alarm, current) ". "
	VALUES ('$temperature', '$heating', '$alarm_on', NOW())";
	
//	$sql2 = "INSERT INTO Users(pi_ip)
//	VALUES ('localhost:8080')";

	mysql_select_db($dbname);
	$retval = mysql_query($sql, $conn);
//	$retval2 = mysql_query($sql2, $conn);
	
	if(! $retval) {
		
		die('Could not execute statement: ' . mysql_error());
	}
	
//	if(! $retval2) {
//		
//		die('Could not execute statement: ' . mysql_error());
//	}
	
	echo "Successfully inserted data";

	mysql_close($conn);
	
	echo nl2br ("\n<a href=".$link1.">Keep inserting</a>");
	echo nl2br ("\n<a href=".$link2.">View Table</a>");
?> 