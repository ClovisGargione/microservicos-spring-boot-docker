/**
 * 
 */
define([], function() {
    'use strict';

    var loginService = function($auth) {
    	
        this.login = function(object) {
            return $auth.login(object);
        };
        
        this.signup = function(object){
            return $auth.signup(object);
        }
    };

    loginService.$inject = ['$auth'];

    return loginService;
});