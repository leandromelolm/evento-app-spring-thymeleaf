$(document).ready(function () {
	$('.sidenav').sidenav();
}); 

$(document).ready(function () {
	$(".dropdown-trigger").dropdown()
});

// detalhesEvento
function confirmarExclusao() {
	var resultado = (confirm('Tem certeza que você quer excluir esse participante?'))
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
