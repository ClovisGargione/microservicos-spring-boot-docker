/**
 * 
 */
define([], function() {
    'use strict';

    var usuariosService = function($http, CONSTANT) {
    	
    	var url = CONSTANT.url;
    	var lista = "usuario/lista";
    	
    	 this.listaDeUsuarios = function() {
             return $http.get(url+lista);
         };
    	
    };

    usuariosService.$inject = ['$http', 'CONSTANT'];

    return usuariosService;
});