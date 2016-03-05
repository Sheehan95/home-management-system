<!DOCTYPE html>
<html>
	<body>
		<?php
		file_get_contents("http://192.168.0.30:8080/temperature");
		echo $http_response_header[3] . "<br>";
		file_get_contents("http://192.168.0.30:8080/motion");
		echo "coordinates: { " . $http_response_header[3] . ", " . $http_response_header[4] . ", " . $http_response_header[5] . " }<br>";
		file_get_contents("http://192.168.0.30:8080/alarm");
		echo $http_response_header[3] . "<br>";
		?>
	</body>
</html>