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

function confirmaDeslogar() {	
	var resultado = (confirm('Tem certeza que deseja deslogar?'))
	if (resultado){
		return true		
	}else{
		return false
	}
}

function nomeUsuarioLogado() {
	console.log("function called....");
	let nome = document.getElementById("username").value
	console.log(nome)
	//Função do Ajax para enviar uma solicitação get
	$.ajax({
		type: "POST",
		url: "/info-user-logged",
		data: nome,  // usuario-padrao%40email.com=	   
		dataType: "text",
		//data: JSON.stringify(nome), // %22usuario-padrao%40email.com%22=
		//dataType: 'json',
		//success: function(){
		//	alert("success");
		//}
	});
}
