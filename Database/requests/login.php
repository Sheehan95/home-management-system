<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "raspberry";

	$data = file_get_contents('php://input');
	$post = json_decode($data, true);
	
	$user = $post["username"];
	$pass = $post["password"];
	
	 // Create connection
	$conn = mysqli_connect($servername, $username, $password);
	 // Check connection
	if (! $conn) {
		die("Connection failed: " . mysqli_error($conn));
	}
	
	$json = array();
	
	mysqli_select_db($conn, $dbname);
	$sql = "SELECT * FROM IPs
			WHERE id IN (SELECT id
						FROM User_Info
						WHERE username ='".$user."'
						AND password ='".$pass."');";
						
	$result = mysqli_query($conn, $sql);
	$ip = mysqli_fetch_assoc($result);
	
	if($ip["pi_ip"] == NULL) {
		
		$bus = array(
			"success" => false,
			"ip" => ""
		);
		array_push($json, $bus);
	}
	else {
		
		$bus = array(
			"success" => true,
			"ip" => $ip["pi_ip"]
		);
		array_push($json, $bus);
	}
	mysqli_close($conn);
	
	$stringjson = json_encode($json);
	
	echo $stringjson;
?>
