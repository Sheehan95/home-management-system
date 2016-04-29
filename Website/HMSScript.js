//Fills table with dummy data
for (var i = 1; i <= 100; ++i) {
	$("#mainTable").append(
		"<tr>" +
				"<td><div class=\"tableText id\">" + i + "</div></td>" +
				"<td class=\"editable\"><div class=\"tableText name\">Dummy</div></td>" +
				"<td class=\"editable\"><div class=\"tableText address\">Dummy Address</div></td>" +
				"<td><div class=\"tableText\">0</div></td>" +
				"<td class=\"editable\" id=\"alarm\"><div class=\"tableText\">" + 
				"<select>" +
				"<option value=\"Armed\">Armed</option>" +
				"<option value=\"Armed\">Unarmed</option>" +
				"</select>" +
				"</div></td>" +
				"<td><div class=\"tableText\">No Break-ins</div></td>" +
				"<td>" + 
				"<span title=\"Save Changes\"><img class=\"save\" src=\"images/saveicon.png\" height=\"20\" width=\"20\"></span>" +
				"<span title=\"Delete Customer\"><img class=\"delete\" src=\"images/delete.png\" height=\"20\" width=\"20\"></span>" +	
				"</td>" +
		"</tr>"
	);
}

$("#user").click(function() {
	window.location.href = "http://localhost/Website/login.php";
});

$(".delete").click(function() {
	$(this).parents("tr").remove();
});

$(".name").click(function() {
	console.log("You clicked name!");
	$(this).parent().html("<input class=\"nameInput\" type=\"text\" value=\"" + $(this).text() + "\">");
});

$(".editable").on("click", ".name", function(){
   console.log("You clicked name!");
	$(this).parent().html("<input class=\"nameInput\" type=\"text\" value=\"" + $(this).text() + "\">");
});

$(".address").click(function() {
	console.log("You clicked address!");
	$(this).parent().html("<input class=\"addressInput\" type=\"text\" value=\"" + $(this).text() + "\">");
});

$(".editable").on("click", ".address", function(){
   console.log("You clicked address!");
	$(this).parent().html("<input class=\"addressInput\" type=\"text\" value=\"" + $(this).text() + "\">");
});

$(".save").click(function() {
	var name = $(this).parents("tr").children(".editable").children(".nameInput").val();
	var address = $(this).parents("tr").children(".editable").children(".addressInput").val(); 
	var alarm = $(this).parents("tr").children(".editable").children(".alarm").children("select").find(":selected").text();
	var id = $(this).parents("tr").children("td").children(".id").text();
	
	if ($(".editable").has(".nameInput")) {
		$(this).parents("tr").children(".editable").children(".nameInput").parent().html("<div class=\"tableText name\">" + name + "</div>");
	}
	
	if ($(".editable").has(".addressInput")) {
		$(this).parents("tr").children(".editable").children(".addressInput").parent().html("<div class=\"tableText address\">" + address + "</div>");
	}
	
	if (id == "0") {
		if (alarm == "Armed") {
			$.ajax({
			type: 'POST',
			url: 'http://192.168.0.22:8080/Alarm',
			data: '{ "arm": true, "ack": false }',
			dataType: 'json',
			crossDomain: true
			});
		}
		else {
			$.ajax({
			type: 'POST',
			url: 'http://192.168.0.22:8080/Alarm',
			data: '{ "arm": false, "ack": false }',
			dataType: 'json',
			crossDomain: true
			});
		}
	}

});

$(".warning").click(function() {
	var alarm = $(this).parents("tr").children(".editable").children(".alarm").children("select").find(":selected").text();
	var id = $(this).parents("tr").children("td").children(".id").text();
	
	if (id == "0") {
		if (alarm == "Armed") {
			$.ajax({
			type: 'POST',
			url: 'http://192.168.0.22:8080/Alarm',
			data: '{ "arm": true, "ack": true }',
			dataType: 'json',
			crossDomain: true
			});
		}
		else {
			$.ajax({
			type: 'POST',
			url: 'http://192.168.0.22:8080/Alarm',
			data: '{ "arm": false, "ack": true }',
			dataType: 'json',
			crossDomain: true
			});
		}
	}
});