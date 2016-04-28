<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";
	
	$link1 = "insertInputs.php";
	$link2 = "http://localhost:8000/requests";

	 // Create connection
	$conn = mysqli_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysqli_error($conn));
	}

	// sql to create table
	$sql = "SELECT * FROM Temperature;";
	$sql2 = "SELECT * FROM IPs";
	$sql3 = "SELECT * FROM User_Info";
	 
	mysqli_select_db($conn, $dbname);


	$result = mysqli_query($conn, $sql);
	echo "Inputs:<br><br>";
	if (mysqli_num_rows($result) > 0) {

		while($row = mysqli_fetch_assoc($result)) {
			echo $row["id"] . " &nbsp &nbsp " . $row["temp"] . " &nbsp &nbsp " . $row["date"] . "<br>";
		}
	}

	$result2 = mysqli_query($conn, $sql2);
	echo "<br>Pi Information:<br><br>";
	if (mysqli_num_rows($result2) > 0) {

		while($row = mysqli_fetch_assoc($result2)) {
			echo $row["id"] . " &nbsp &nbsp " . $row["pi_ip"]. "<br>";
		}
	}

	$result3 = mysqli_query($conn, $sql3);
	echo "<br>User Details:</br></br>";
	if(mysqli_num_rows($result3) > 0) {

		while($row = mysqli_fetch_assoc($result3)) {
			echo $row["id"] . " &nbsp &nbsp " . $row["username"] . " &nbsp &nbsp " . $row["password"] . "<br>";
		}
	}

	mysqli_close($conn);
	
	echo nl2br ("\n<a href=".$link1.">Back to inserting</a>");
	echo nl2br ("\n<a href=".$link2.">HOME</a>");
?>
