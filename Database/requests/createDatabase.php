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
   
	echo 'Connected successfully';
   
	$sql = "CREATE Database raspberry";
	$create = mysqli_query($conn, $sql);
   
	if(! $create ) {

		echo "Failed";
		header("Location: option.php");
	}
	else {

		mysqli_close($conn);
	
		header("Location: createTable.php");
	}
   
	//echo "Database ".$dbname." created successfully\n";
	
?>
