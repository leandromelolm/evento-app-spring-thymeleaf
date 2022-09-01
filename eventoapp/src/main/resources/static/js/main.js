$(document).ready(function () {
	$('.sidenav').sidenav();
}); 

$(document).ready(function () {
	$(".dropdown-trigger").dropdown()
});

// detalhesEvento, listaParticipantes
function confirmarExcluirParticipante() {
	var resultado = (confirm('Tem certeza que você quer excluir este participante?'))
	if (resultado == true) {
		return true;
	} else {
		return false;
	}
}

// meus-eventos,listaEventos
function confirmarExcluirEvento() {
	var resultado = (confirm('Tem certeza que você quer excluir este evento?'))
	if (resultado == true) {
		return true;
	} else {
		return false;
	} 
}

// minha-conta, login
function confirmaDeslogar() {	
	var resultado = (confirm('Tem certeza que deseja deslogar?'))
	if (resultado){
		return true		
	}else{
		return false
	}
}
