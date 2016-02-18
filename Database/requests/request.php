<?php

	$ch = curl_init();
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_URL, 
		'http://192.168.1.8:8080'
	);
	$content = curl_exec($ch);
	echo $content;
	
?>