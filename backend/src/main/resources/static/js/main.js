$(document).ready(function () {
	$('.sidenav').sidenav();
}); 

$(document).ready(function () {
	$(".dropdown-trigger").dropdown()
});

// metodo usando na pagina detalhesEvento
function confirmarExclusao() {
	var resultado = (confirm('Tem certeza que você quer excluir esse participante?'))
	if (resultado == true) {
		return true;
	} else {
		return false;
	}
}