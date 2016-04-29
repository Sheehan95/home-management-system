$("#submit").click(function() {
	if ($("#username").val() !== "employee" && $("#password").val() !== "employee") {
		alert("Invalid information entered. Please try again.");
	}
});