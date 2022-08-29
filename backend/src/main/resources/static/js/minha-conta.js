function confirmarExcluir() {
	var resultado = (confirm('Tem certeza que você quer excluir sua Conta? Sua conta será deslogada e seus dados apagados. Para acessar novamente você precisará fazer um novo cadastro.'))
	if (resultado == true) {
		alert("Clique em OK para Excluir sua conta!")
	return true;
	}else {
		return false;
	}
}