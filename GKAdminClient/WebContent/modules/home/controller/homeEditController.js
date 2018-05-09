/**
 * 
 */
define(['moment'], function(moment) {
	'use strict';

	var homeEditController = function(homeService, usuario) {
		var vm = this;
		vm.usuario = usuario.data;
		vm.perfis = listaPerfis();
		vm.existeNoPerfil = jaFoiSelecionado;
		vm.selecionaPerfil = selecionaPerfil;
		vm.dataAlteracaoSenha = moment(vm.usuario.lastPasswordResetDate).format("DD/MM/YYYY");
		console.log(vm.usuario);
		var perfisDoUsuario = vm.usuario.authorities;
		
		function listaPerfis(){
			var perfis = [{
				id: "ROLE_USER",
				label: "Usuário"
			},
			{
				id: "ROLE_ADMIN",
				label: "Administrador"
			},
			{
				id: "ROLE_SUPER_ADMIN",
				label: "Super administrador"
			}];
			return perfis;
		}
		
		function jaFoiSelecionado(perfil){
			for(var index = 0; index < perfisDoUsuario.length; index++){
				if(perfisDoUsuario[index].authority == perfil){
					return true;
				}
			}
			return false;
		}
		
		function removePerfil(perfil){
			for(var index = 0; index < perfisDoUsuario.length; index++){
				if(perfisDoUsuario[index].authority == perfil){
					perfisDoUsuario.splice(index, 1);
					return;
				}
			}
		}
		
		function adicionaPerfil(perfil){
			perfisDoUsuario.push({"authority": perfil});
		}
		
		function selecionaPerfil(perfil){
			if(jaFoiSelecionado(perfil)){
				removePerfil(perfil);
			}else{
				adicionaPerfil(perfil);
			}
		}
				
	};
	homeEditController.$inject = [ 'homeService', 'usuario' ];

	return homeEditController;

});