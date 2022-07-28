var currYear = (new Date()).getFullYear();

$(document).ready(function() {
	$(".datepicker").datepicker({
		defaultDate: new Date(currYear - 5, 1, 31),
		// setDefaultDate: new Date(2000,01,31),
		maxDate: new Date(currYear - 5, 12, 31),
		format: "dd/mm/yyyy",
		autoClose: true,
		showDaysInNextAndPreviousMonths: true
	});
});

function alertaSair() {	
	alert("Deslogado");
}

function clickEvent() {	
	alert("Test");	
}