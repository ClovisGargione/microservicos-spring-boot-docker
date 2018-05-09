/**
 * 
 */
define([], function() {
    'use strict';

    var homeService = function($http, CONSTANT) {
    	
    	var url = CONSTANT.url;
    	var lista = "usuario/lista";
    	var usuario = "usuario/"
    	
    	 this.listaDeUsuarios = function() {
             return $http.get(url+lista);
         };
         
         this.getUsuario = function(id, tenant) {
             return $http.get(url+usuario+id+"/"+tenant);
         };
    	
    };

    homeService.$inject = ['$http', 'CONSTANT'];

    return homeService;
});