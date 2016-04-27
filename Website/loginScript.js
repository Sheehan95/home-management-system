$("#submit").click(function() {
	console.log($("#username").val());
	console.log($("#password").val());
	if ($("#username").val() !== "employee" && $("#password").val() !== "employee") {
		alert("Invalid information entered. Please try again.");
	}
});