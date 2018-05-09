/**
 * 
 */
define([], function() {
	'use strict';

	var usuariosController = function(usuariosService) {
		var vm = this;
		vm.usuarios = [];
		
		function listaDeUsuarios(){
			usuariosService.listaDeUsuarios()
			.then(function(response){
				vm.usuarios = response.data;
			})
			.catch(function(error){
				
			});
		}
		
		listaDeUsuarios();

	};
	usuariosController.$inject = [ 'usuariosService'];

	return usuariosController;

});