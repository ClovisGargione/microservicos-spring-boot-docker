/**
 * 
 */
define([], function() {
	'use strict';

	var homeController = function(homeService, usuarios) {
		var vm = this;
		vm.usuarios = usuarios.data;
	};
	homeController.$inject = [ 'homeService', 'usuarios' ];

	return homeController;

});