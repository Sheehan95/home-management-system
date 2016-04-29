<!DOCTYPE html>
<html>
	<head>
		<title>Home Management System</title>
		<link rel="stylesheet" type="text/css" href="HMSStyle.css">
		<link rel="icon" href="images/favicon.png">
	</head>
	<body>
		
		<?php
		if ($_POST["username"] != "employee" && $_POST["password"] != "employee") {
			header("Location: login.php");
			exit();
		}
		?>
		
		<div id="banner">
			<h2>Home Management System</h2>
			<div id="user"><img src="images/usericon.png" height="30" width="30"><?php echo $_POST["username"]; ?></div>
		</div>
		
		<table id="mainTable">
			<tr>
				<th><div class="tableText">ID</div></th>
				<th><div class="tableText">Owner</div></th>
				<th><div class="tableText">Address</div></th>
				<th><div class="tableText">Temperature</div></th>
				<th><div class="tableText">Alarm</div></th>
				<th><div class="tableText">Alarm Status</div></th>
				<th></th>
			</tr>
			<tr>
				<td><div class="tableText id">0</div></td>
				<td class="editable"><div class="tableText name">Robert O'Riordan</div></td>
				<td class="editable"><div class="tableText address">11A Landscape Park, Pouladuff Road</div></td>
				<td><div class="tableText">
					<?php
						$json = file_get_contents('http://localhost:8080/Temperature');
						$obj = json_decode($json);
						echo $obj->temperature;
					?>
				</div></td>
				<td class="editable"><div class="tableText alarm">
				<select>
				<option value="Armed">Armed</option>
				<option value="Armed" 
					<?php
						$json = file_get_contents('http://localhost:8080/Alarm');
						$obj = json_decode($json);
						if ($obj->alarm_armed == false) {
							echo "selected=\"selected\"";
						}
					?>
				>Unarmed</option>
				</select>
				</div></td>
				<td><div class="tableText">
					<?php
						$json = file_get_contents('http://localhost:8080/Alarm');
						$obj = json_decode($json);
						if ($obj->break_in == true) {
							echo "Break-in occurred!<span title=\"Acknowlege Break-in\"><img class=\"warning\" src=\"images/warning.png\" height=\"20\" width=\"20\"></span>";
						}
						else {
							echo "No Break-ins";
						}
					?>
				</div></td>
				<td>
				<span title="Save Changes"><img class="save" src="images/saveicon.png" height="20" width="20"></span>
				<span title="Delete Customer"><img class="delete" src="images/delete.png" height="20" width="20"></span>	
				</td>
			</tr>
		</table>
	</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script type="text/javascript" src="HMSScript.js"></script>
</html>