<?php

	$content = file_get_contents("http://localhost:8080/Temperature");
	$temp = json_decode($content, true);
	
	$current_date = $temp['date']['year'] . "-";
	$current_date .= $temp['date']['month'] . "-";
	$current_date .= $temp['date']['day'] . " ";
	$current_date .= $temp['date']['hour'] . ":";
	$current_date .= $temp['date']['minute'] . ":";
	$current_date .= $temp['date']['second'];
	
	echo $current_date;
	
	echo '<br>';
	
	echo $temp['temperature'];
	
	echo '<br>';
	
	$bool = (int)$temp['heating'];
	echo $bool;
	
?>