//Fills table with dummy data
for (var i = 1; i <= 100; ++i) {
	$("#mainTable").append(
		"<tr>" +
				"<td><div class=\"tableText\">" + i + "</div></td>" +
				"<td class=\"editable\"><div class=\"tableText name\">Robert O'Riordan</div></td>" +
				"<td class=\"editable\"><div class=\"tableText address\">11A Landscape Park, Pouladuff Road</div></td>" +
				"<td><div class=\"tableText\">20</div></td>" +
				"<td><div class=\"tableText\">0</div</td>" +
				"<td class=\"editable\" id=\"alarm\"><div class=\"tableText\">" + 
				"<select>" +
				"<option value=\"Armed\">Armed</option>" +
				"<option value=\"Armed\" selected=\"selected\">Unarmed</option>" +
				"</select>" +
				"</div></td>" +
				"<td><span title=\"Save Changes\"><img class=\"save\" src=\"images/saveicon.png\" height=\"20\" width=\"20\"></span>" +
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
	
	if ($(".editable").has(".nameInput")) {
		$(".nameInput").parent().html("<div class=\"tableText name\">" + name + "</div>");
	}
	
	if ($(".editable").has(".addressInput")) {
		$(".addressInput").parent().html("<div class=\"tableText address\">" + address + "</div>");
	}
});